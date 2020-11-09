package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CalActivity extends AppCompatActivity {
    private TextView doneBtnText, backBtnText;
    private CalendarView calendar;

    private String selectedDate = LocalDate.now().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);

        setBtnOnClick();

        calendar = findViewById(R.id.calendar);
        Date now = new Date();
        calendar.setMinDate(now.getTime());
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = year + "-" + (month+1) + "-";
                if (dayOfMonth < 10) {
                    selectedDate = selectedDate + "0";
                } selectedDate = selectedDate + dayOfMonth;
            }
        });
    }

    private void setBtnOnClick() {
        backBtnText = findViewById(R.id.backBtnText);
        doneBtnText = findViewById(R.id.doneBtnText);

        backBtnText.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        });
        doneBtnText.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", selectedDate);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        });

    }
}