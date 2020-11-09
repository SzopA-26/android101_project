package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class AllActivity extends AppCompatActivity {
    private ImageView todayBtnImg, newBtnImg, statBtnImg, hisBtnImg;
    private TextView todayBtnText, newBtnText, statBtnText, hisBtnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        setMenuBtnOnClick();
    }

    private void setMenuBtnOnClick() {
        todayBtnImg = findViewById(R.id.todayBtnImg);
        newBtnImg = findViewById(R.id.newBtnImg);
        statBtnImg = findViewById(R.id.statBtnImg);
        hisBtnImg = findViewById(R.id.hisBtnImg);

        todayBtnText = findViewById(R.id.todayBtnText);
        newBtnText = findViewById(R.id.newBtnText);
        statBtnText = findViewById(R.id.statBtnText);
        hisBtnText = findViewById(R.id.hisBtnText);

        todayBtnImg.setOnClickListener(v -> startActivity(new Intent(AllActivity.this, TodayActivity.class)));
        todayBtnText.setOnClickListener(v -> startActivity(new Intent(AllActivity.this, TodayActivity.class)));
        newBtnImg.setOnClickListener(v -> startActivity(new Intent(AllActivity.this, NewActivity.class)));
        newBtnText.setOnClickListener(v -> startActivity(new Intent(AllActivity.this, NewActivity.class)));
        statBtnImg.setOnClickListener(v -> startActivity(new Intent(AllActivity.this, StatActivity.class)));
        statBtnText.setOnClickListener(v -> startActivity(new Intent(AllActivity.this, StatActivity.class)));
        hisBtnImg.setOnClickListener(v -> startActivity(new Intent(AllActivity.this, HistoryActivity.class)));
        hisBtnText.setOnClickListener(v -> startActivity(new Intent(AllActivity.this, HistoryActivity.class)));
    }
}