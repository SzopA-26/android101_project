package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class StatActivity extends AppCompatActivity {
    private ImageView todayBtnImg, allBtnImg, newBtnImg, hisBtnImg;
    private TextView todayBtnText, allBtnText, newBtnText, hisBtnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        setMenuBtnOnClick();
    }

    private void setMenuBtnOnClick() {
        todayBtnImg = findViewById(R.id.todayBtnImg);
        allBtnImg = findViewById(R.id.allBtnImg);
        newBtnImg = findViewById(R.id.newBtnImg);
        hisBtnImg = findViewById(R.id.hisBtnImg);

        todayBtnText = findViewById(R.id.todayBtnText);
        allBtnText = findViewById(R.id.allBtnText);
        newBtnText = findViewById(R.id.newBtnText);
        hisBtnText = findViewById(R.id.hisBtnText);

        allBtnImg.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, AllActivity.class)));
        allBtnText.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, AllActivity.class)));
        newBtnImg.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, NewActivity.class)));
        newBtnText.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, NewActivity.class)));
        todayBtnImg.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, TodayActivity.class)));
        todayBtnText.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, TodayActivity.class)));
        hisBtnImg.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, HistoryActivity.class)));
        hisBtnText.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, HistoryActivity.class)));
    }
}