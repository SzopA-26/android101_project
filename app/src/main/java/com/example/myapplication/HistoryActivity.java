package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

import java.util.Collections;
import java.util.List;

public class HistoryActivity extends ActivityShowList {
    private LinearLayout listLayout;
    private ItemDatabase appDB;
    private List<Item> items;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        appDB = ItemDatabase.getInstance(this);

        listLayout = findViewById(R.id.listLayout);
        ConstraintLayout emptyText = findViewById(R.id.emptyText);
        items = getItemList();
        setItemList(items);
        if (items.size() == 0) {
            emptyText.findViewById(R.id.emptyText);
            listLayout.addView(emptyText);
        }

        setClearBtnOnClick();
        setMenuBtnOnClick();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Item> getItemList() {
        List<Item> items = appDB.itemDao().getItemAllList(false);
        items.sort(new DateComparator());
        Collections.reverse(items);
        return items;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setItemList(List<Item> items) {
        Log.i("ii", "his-amount: " + items.size());
        Log.i("ii", "his-items: " + items.toString());

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

            time.setText(DataManager.getDisplayDateFormat(DataManager.stringToLocalDateTime(item.getDate()), false) + " " + item.getTime()); //time
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

    @Override
    public void setItemOnClick(LinearLayout item, int itemId) {
        item.setOnClickListener(v -> {
            ProgressDialog dialog = new ProgressDialog();
            dialog.setItemId(itemId);
            dialog.setActivity(this);
            dialog.show(getSupportFragmentManager(), "ProgressDialog");
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setClearBtnOnClick() {
        TextView clearBtnText = findViewById(R.id.clearBtnText);
        clearBtnText.setOnClickListener(v -> {
            if (getItemList().size() > 0) {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Do you want to delete all task ?")
                        .setMessage("This will remove all task from history, you can't get it back later.")
                        .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("ii", "onClick: " + "delete item");
                                appDB.itemDao().deleteAllItem();
                                setItemList(getItemList());
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void setMenuBtnOnClick() {
        ImageView todayBtnImg = findViewById(R.id.todayBtnImg);
        ImageView allBtnImg = findViewById(R.id.allBtnImg);
        ImageView newBtnImg = findViewById(R.id.newBtnImg);
        ImageView statBtnImg = findViewById(R.id.statBtnImg);

        TextView todayBtnText = findViewById(R.id.todayBtnText);
        TextView allBtnText = findViewById(R.id.allBtnText);
        TextView newBtnText = findViewById(R.id.newBtnText);
        TextView statBtnText = findViewById(R.id.statBtnText);

        allBtnImg.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, AllActivity.class)));
        allBtnText.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, AllActivity.class)));
        newBtnImg.setOnClickListener(v -> startActivityForResult(new Intent(HistoryActivity.this, NewActivity.class), DataManager.LAUNCH_NEW));
        newBtnText.setOnClickListener(v -> startActivityForResult(new Intent(HistoryActivity.this, NewActivity.class), DataManager.LAUNCH_NEW));
        statBtnImg.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, StatActivity.class)));
        statBtnText.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, StatActivity.class)));
        todayBtnImg.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, TodayActivity.class)));
        todayBtnText.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, TodayActivity.class)));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DataManager.LAUNCH_NEW) {
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