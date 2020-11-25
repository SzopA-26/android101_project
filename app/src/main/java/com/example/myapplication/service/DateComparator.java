package com.example.myapplication.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.model.Item;
import com.google.common.collect.Comparators;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

public class DateComparator implements Comparator<Item> {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compare(Item o1, Item o2) {
        LocalDate o1Date = DataManager.stringToLocalDate(o1.getDate());
        LocalDate o2Date = DataManager.stringToLocalDate(o2.getDate());
        if (o1Date.compareTo(o2Date) == 0) {
            if (o1.getStart() == null) {
                return -1;
            }
            if (o2.getStart() == null) {
                return 1;
            }
            LocalDateTime o1Time = DataManager.stringToLocalDateTime(o1.getStart(),o1.getDate());
            LocalDateTime o2Time = DataManager.stringToLocalDateTime(o2.getStart(),o2.getDate());
            return o1Time.compareTo(o2Time);
        }
        return o1Date.compareTo(o2Date);
    }

}
