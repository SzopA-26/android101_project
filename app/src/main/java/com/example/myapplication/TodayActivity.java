package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.model.Item;
import com.example.myapplication.service.DimenManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TodayActivity extends AppCompatActivity {
    private ImageView allBtnImg, newBtnImg, statBtnImg, hisBtnImg;
    private TextView allBtnText, newBtnText, statBtnText, hisBtnText;
    private LinearLayout listLayout;

    private Button testBtn;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        setMenuBtnOnClick();
        setTestBtn();
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
        newBtnImg.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, NewActivity.class)));
        newBtnText.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, NewActivity.class)));
        statBtnImg.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, StatActivity.class)));
        statBtnText.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, StatActivity.class)));
        hisBtnImg.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, HistoryActivity.class)));
        hisBtnText.setOnClickListener(v -> startActivity(new Intent(TodayActivity.this, HistoryActivity.class)));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTestBtn() {
        Item testItem = new Item("Work", LocalDate.now(), R.drawable.work, R.color.white);

        testBtn = findViewById(R.id.testBtn);
        listLayout = findViewById(R.id.listLayout);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                LinearLayout item = new LinearLayout(getApplicationContext());
                item.setOrientation(LinearLayout.HORIZONTAL);
                item.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_square_solid));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DimenManager.getItemWidth(getApplicationContext()), DimenManager.getItemHeight(getApplicationContext()));
                layoutParams.setMargins(0, DimenManager.getItemMarginTop(getApplicationContext()),0,0);
                item.setLayoutParams(layoutParams);

                ImageView img = new ImageView(getApplicationContext());
                img.setImageResource(testItem.getIcon()); //icon
                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(DimenManager.getIconWidth(getApplicationContext()), LinearLayout.LayoutParams.MATCH_PARENT);
                int margin = DimenManager.getIconMargin(getApplicationContext());
                imgParams.setMargins(margin, margin, margin, margin);
                imgParams.weight = 1;
                img.setLayoutParams(imgParams);

                LinearLayout detail = new LinearLayout(getApplicationContext());
                detail.setOrientation(LinearLayout.VERTICAL);
                detail.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_square_solid));
                LinearLayout.LayoutParams detailParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                detailParams.weight = 1;
                detail.setLayoutParams(detailParams);

                TextView name = new TextView(getApplicationContext());
                name.setText(testItem.getName()); //name
                name.setTextSize(DimenManager.itemTextSize);
                name.setTextColor(DimenManager.itemTextColor);
                LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
                nameParams.setMargins(0,12,0,0);
                nameParams.gravity = Gravity.BOTTOM;
                nameParams.weight = 1;
                name.setLayoutParams(nameParams);

                TextView time = new TextView(getApplicationContext());

                time.setText(" " + testItem.getTime()); //time
                time.setTextSize(DimenManager.itemTextSize);
                time.setTextColor(DimenManager.itemTextColor);
                LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
                timeParams.setMargins(0,0,0,0);
                timeParams.gravity = Gravity.TOP;
                timeParams.weight = 1;
                time.setLayoutParams(timeParams);

                item.addView(img);
                detail.addView(name);
                detail.addView(time);
                item.addView(detail);
                listLayout.addView(item);

            }
        });
    }

}