package com.example.myapplication.service;

import android.app.Activity;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.Item;

import java.util.List;

public abstract class ActivityShowList extends AppCompatActivity {
    public abstract List<Item> getItemList();
    public abstract void setItemList(List<Item> items);
    protected abstract void setItemOnClick(LinearLayout item, int itemId);
}
