package com.example.myapplication.service;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DimenManager {
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
    public static String getTimeFormat(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getTimeFormat(LocalDateTime start, LocalDateTime end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if (end == null) {
            return start.format(formatter);
        }
        return start.format(formatter) + " - " + end.format((formatter));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getFullDateFormat(LocalDateTime dateTime) {
        String pattern = "dd MMMM yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dateToCompare = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth()).atTime(0,0);
        LocalDateTime today = LocalDate.now().atTime(0,0);
        if (dateToCompare.equals(today)) {
            return "Today, " + dateTime.format(formatter);
        }
        return dateTime.format(formatter);
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
