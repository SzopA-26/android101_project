package com.example.myapplication.service;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DataManager {
    public static int itemWidth = 400;
    public static int itemHeight = 80;
    public static int itemMarginTop = 12;
    public static int itemTextSize = 16;
    public static int itemTextColor = Color.WHITE;

    public static int iconWidth = 120;
    public static int iconMargin = 10;

    public static int dpToPx(float dp, Context context) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getDisplayDateFormat(LocalDateTime dateTime, boolean hasYear) {
        String pattern = "dd MMMM";
        if (hasYear) {
            pattern = "dd MMMM yyyy";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dateToCompare = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth()).atTime(0,0);
        LocalDateTime today = LocalDate.now().atTime(0,0);
        LocalDateTime tomorrow = LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth() + 1).atTime(0,0);
        if (dateToCompare.equals(today)) {
            return "Today";
        }
        if (dateToCompare.equals(tomorrow)) {
            return "Tomorrow";
        }
        return dateTime.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String dateTimeToString(LocalDateTime dateTime) {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String dateTimeToFullString(LocalDateTime dateTime) {
        String pattern = "dd MMMM yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime stringToLocalDateTime(String dateStr) {
        String[] date = dateStr.split("-");
        return LocalDateTime.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]),0,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate stringToLocalDate(String dateStr) {
        String[] date = dateStr.split("-");
        return LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime stringToLocalDateTime(String timeStr, String dateStr) {
        LocalDate date = stringToLocalDate(dateStr);
        String[] time = timeStr.split(":");
        return LocalDateTime.of(date, LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1])));
    }

    public static String getStringTime(int h, int m) {
        String time = "";
        if (h < 10) time += "0";
        time += h + ":";
        if (m < 10) time += "0";
        time += m;
        return time;
    }

    public static int getItemWidth(Context context) {
        return dpToPx(itemWidth, context);
    }

    public static int getItemHeight(Context context) {
        return dpToPx(itemHeight, context);
    }

    public static int getItemMarginTop(Context context) {
        return dpToPx(itemMarginTop, context);
    }

    public static int getIconWidth(Context context) {
        return dpToPx(iconWidth, context);
    }

    public static int getIconMargin(Context context) {
        return dpToPx(iconMargin, context);
    }
}
