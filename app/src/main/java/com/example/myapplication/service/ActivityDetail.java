package com.example.myapplication.service;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.NewActivity;

public abstract class ActivityDetail extends AppCompatActivity {
    public abstract void setActivityIcon(int icon);
    public abstract void setActivityColor(int color);
    public abstract void setActivityDate(String date);
}
