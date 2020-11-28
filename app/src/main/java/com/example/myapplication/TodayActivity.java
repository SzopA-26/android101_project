package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.component.ProgressDialog;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.ItemDatabase;
import com.example.myapplication.service.ActivityShowList;
import com.example.myapplication.service.DataManager;
import com.example.myapplication.service.DateComparator;
import com.example.myapplication.service.MyReceiver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TodayActivity extends ActivityShowList {
    private LinearLayout listLayout;
    private ItemDatabase appDB;
    private List<Item> items;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        setTodayDisplay();

        appDB = ItemDatabase.getInstance(this);

        setItemHistory();

        listLayout = findViewById(R.id.listLayout);
        ConstraintLayout emptyText = findViewById(R.id.emptyText);
        items = getItemList();
        setItemList(items);
        if (items.size() == 0) {
            emptyText.findViewById(R.id.emptyText);
            listLayout.addView(emptyText);
        }
        setMenuBtnOnClick();

        // set alarm notification
        MyReceiver alarm = new MyReceiver();
        alarm.setAlarm(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setItemHistory() {
        List<Item> allAvailableItem = appDB.itemDao().getItemAllList(true);
        for (Item item : allAvailableItem) {
            if (DataManager.stringToLocalDate(item.getDate()).compareTo(LocalDate.now()) < 0) {
                item.setAvailable(false);
                appDB.itemDao().updateItem(item);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTodayDisplay() {
        TextView dateText = findViewById(R.id.dateText);
        TextView dayText = findViewById(R.id.dayText);
        TextView monthText = findViewById(R.id.monthText);
        String dayOfMonth = "";
        if (LocalDate.now().getDayOfMonth() < 10) {
            dayOfMonth += "0";
        } dayOfMonth += LocalDate.now().getDayOfMonth();
        dateText.setText(dayOfMonth);

        dayText.setAllCaps(false);monthText.setAllCaps(false);
        String dayUpper = LocalDate.now().getDayOfWeek().toString();
        int dayLength = dayUpper.length();
        String dayCap = dayUpper.charAt(0) + dayUpper.substring(1, dayLength).toLowerCase();
        dayText.setText(dayCap);

        String monthUpper = LocalDate.now().getMonth().toString();
        int monthLength = monthUpper.length();
        String monthCap = monthUpper.charAt(0) + monthUpper.substring(1, monthLength).toLowerCase();
        monthText.setText(monthCap+ " " + LocalDate.now().getYear());
    }

    private void setMenuBtnOnClick() {
        ImageView allBtnImg = findViewById(R.id.allBtnImg);
        ImageView newBtnImg = findViewById(R.id.newBtnImg);
        ImageView statBtnImg = findViewById(R.id.statBtnImg);
        ImageView hisBtnImg = findViewById(R.id.hisBtnImg);

        TextView allBtnText = findViewById(R.id.allBtnText);
        TextView newBtnText = findViewById(R.id.newBtnText);
        TextView statBtnText = findViewById(R.id.statBtnText);
        TextView hisBtnText = findViewById(R.id.hisBtnText);

        allBtnImg.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, AllActivity.class)));
        allBtnText.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, AllActivity.class)));
        newBtnImg.setOnClickListener(v -> startActivityForResult(new Intent(TodayActivity.this, NewActivity.class), DataManager.LAUNCH_NEW));
        newBtnText.setOnClickListener(v -> startActivityForResult(new Intent(TodayActivity.this, NewActivity.class), DataManager.LAUNCH_NEW));
        statBtnImg.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, StatActivity.class)));
        statBtnText.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, StatActivity.class)));
        hisBtnImg.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, HistoryActivity.class)));
        hisBtnText.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, HistoryActivity.class)));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Item> getItemList() {
        List<Item> items = appDB.itemDao().getItemListByDate(DataManager.dateTimeToString(LocalDateTime.now()), true);
        items.sort(new DateComparator());
        return items;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setItemList(List<Item> items) {
        Log.i("ii", "today-amount: " + items.size());
        Log.i("ii", "today-items: " + items.toString());

        listLayout.removeAllViews();
        for (Item item : items) {
            LinearLayout linear = new LinearLayout(this);
            linear.setOrientation(LinearLayout.HORIZONTAL);
            linear.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_square_solid));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DataManager.getItemWidth(getApplicationContext()), DataManager.getItemHeight(getApplicationContext()));
            layoutParams.setMargins(0, DataManager.getItemMarginTop(getApplicationContext()),0,0);
            linear.setLayoutParams(layoutParams);

            ImageView img = new ImageView(getApplicationContext());
            img.setImageResource(item.getIcon()); //icon
            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(DataManager.getIconWidth(getApplicationContext()), LinearLayout.LayoutParams.MATCH_PARENT);
            int margin = DataManager.getIconMargin(getApplicationContext());
            imgParams.setMargins(margin, margin, margin, margin);
            imgParams.weight = 1;
            img.setLayoutParams(imgParams);
            img.setColorFilter(ContextCompat.getColor(this, item.getColor()), android.graphics.PorterDuff.Mode.MULTIPLY);

            LinearLayout detail = new LinearLayout(getApplicationContext());
            detail.setOrientation(LinearLayout.VERTICAL);
            detail.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_square_solid));
            LinearLayout.LayoutParams detailParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            detailParams.weight = 1;
            detail.setLayoutParams(detailParams);

            TextView name = new TextView(getApplicationContext());
            name.setText(item.getName()); //name
            name.setTextSize(DataManager.itemTextSize);
            name.setTextColor(DataManager.itemTextColor);
            LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
            nameParams.setMargins(0,12,0,0);
            nameParams.gravity = Gravity.BOTTOM;
            nameParams.weight = 1;
            name.setLayoutParams(nameParams);

            TextView time = new TextView(getApplicationContext());

            time.setText(item.getTime()); //time
            if (item.getTime().equals("")) time.setText("-");
            time.setTextSize(DataManager.itemTextSize);
            time.setTextColor(DataManager.itemTextColor);
            LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
            timeParams.setMargins(0,0,0,0);
            timeParams.gravity = Gravity.TOP;
            timeParams.weight = 1;
            time.setLayoutParams(timeParams);

            linear.addView(img);
            detail.addView(name);
            detail.addView(time);
            linear.addView(detail);
            listLayout.addView(linear);

            setItemOnClick(linear, item.getId());
        }
    }

    public void setItemOnClick(LinearLayout item, int itemId) {
        item.setOnClickListener(v -> {
            ProgressDialog dialog = new ProgressDialog();
            dialog.setItemId(itemId);
            dialog.setActivity(this);
            dialog.show(getSupportFragmentManager(), "ProgressDialog");
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DataManager.LAUNCH_NEW) {
            if(resultCode == Activity.RESULT_OK){
                listLayout.removeAllViews();
                items = getItemList();
                setItemList(items);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if (requestCode == DataManager.LAUNCH_EDIT) {
            if (resultCode == Activity.RESULT_OK) {
                listLayout.removeAllViews();
                items = getItemList();
                setItemList(items);
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}