package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.component.IconDialog;
import com.example.myapplication.component.ProgressDialog;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.ItemDatabase;
import com.example.myapplication.service.DataManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodayActivity extends AppCompatActivity {
    private ImageView allBtnImg, newBtnImg, statBtnImg, hisBtnImg;
    private TextView allBtnText, newBtnText, statBtnText, hisBtnText;
    private LinearLayout listLayout;
    private ItemDatabase appDB;
    private List<Item> items;

    private int LAUNCH_NEW = 1;

    private Button testBtn;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        appDB = ItemDatabase.getInstance(this);

        listLayout = findViewById(R.id.listLayout);
        items = appDB.itemDao().getItemTodayList(DataManager.dateTimeToString(LocalDateTime.now()), true);
        setItemList(items);

        setMenuBtnOnClick();
        setTestBtn();
    }

    private void setTestBtn() {
        testBtn = findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                appDB.itemDao().deleteAllItem();

//                Item testItem = new Item("History Testing", "2020-11-12", 2131165303, 2131034161);
//                testItem.setAvailable(false);
//                appDB.itemDao().insertItem(testItem);

//                startActivity(new Intent(TodayActivity.this, EditActivity.class));


            }
        });
    }

    private void setMenuBtnOnClick() {
        allBtnImg = findViewById(R.id.allBtnImg);
        newBtnImg = findViewById(R.id.newBtnImg);
        statBtnImg = findViewById(R.id.statBtnImg);
        hisBtnImg = findViewById(R.id.hisBtnImg);

        allBtnText = findViewById(R.id.allBtnText);
        newBtnText = findViewById(R.id.newBtnText);
        statBtnText = findViewById(R.id.statBtnText);
        hisBtnText = findViewById(R.id.hisBtnText);

        allBtnImg.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, AllActivity.class)));
        allBtnText.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, AllActivity.class)));
        newBtnImg.setOnClickListener(v -> startActivityForResult(new Intent(TodayActivity.this, NewActivity.class), LAUNCH_NEW));
        newBtnText.setOnClickListener(v -> startActivityForResult(new Intent(TodayActivity.this, NewActivity.class), LAUNCH_NEW));
        statBtnImg.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, StatActivity.class)));
        statBtnText.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, StatActivity.class)));
        hisBtnImg.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, HistoryActivity.class)));
        hisBtnText.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, HistoryActivity.class)));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_NEW) {
            if(resultCode == Activity.RESULT_OK){
                listLayout.removeAllViews();
                items = appDB.itemDao().getItemTodayList(DataManager.dateTimeToString(LocalDateTime.now()), true);
                setItemList(items);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    protected void setItemOnClick(LinearLayout item) {
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialog = new ProgressDialog();
                dialog.show(getSupportFragmentManager(), "ProgressDialog");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void setItemList(List<Item> items) {
        Log.i("ii", "today-amount: " + items.size());
        Log.i("ii", "today-items: " + items.toString());

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

            setItemOnClick(linear);
        }

    }

}