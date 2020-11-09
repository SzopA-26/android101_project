package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {
    private ImageView todayBtnImg, allBtnImg, newBtnImg, statBtnImg;
    private TextView todayBtnText, allBtnText, newBtnText, statBtnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setMenuBtnOnClick();
    }

    private void setMenuBtnOnClick() {
        todayBtnImg = findViewById(R.id.todayBtnImg);
        allBtnImg = findViewById(R.id.allBtnImg);
        newBtnImg = findViewById(R.id.newBtnImg);
        statBtnImg = findViewById(R.id.statBtnImg);

        todayBtnText = findViewById(R.id.todayBtnText);
        allBtnText = findViewById(R.id.allBtnText);
        newBtnText = findViewById(R.id.newBtnText);
        statBtnText = findViewById(R.id.statBtnText);

        allBtnImg.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, AllActivity.class)));
        allBtnText.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, AllActivity.class)));
        newBtnImg.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, NewActivity.class)));
        newBtnText.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, NewActivity.class)));
        statBtnImg.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, StatActivity.class)));
        statBtnText.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, StatActivity.class)));
        todayBtnImg.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, TodayActivity.class)));
        todayBtnText.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, TodayActivity.class)));
    }
}