package com.example.myapplication.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.service.DimenManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Item {
    private String name;
    private LocalDate date;
    private LocalDateTime start;
    private LocalDateTime end;
    private int icon, color;
    private boolean isAvailable = true;

    public Item(String name, LocalDate date, int icon, int color) {
        this.name = name;
        this.date = date;
        this.icon = icon;
        this.color = color;
    }

    public Item(String name, LocalDate date, LocalDateTime start, LocalDateTime end, int icon, int color) {
        this.name = name;
        this.date = date;
        this.start = start;
        this.end = end;
        this.icon = icon;
        this.color = color;
    }

    public Item(String name, LocalDate date, LocalDateTime start, int icon, int color) {
        this.name = name;
        this.date = date;
        this.start = start;
        this.icon = icon;
        this.color = color;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTime() {
        if (start == null) {
            return "-";
        }
        if (end == null) {
            return DimenManager.getTimeFormat(start);
        } return DimenManager.getTimeFormat(start) + " - " + DimenManager.getTimeFormat(end);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        if (start == null || start.equals(end)) {
            return;
        }
        this.end = end;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", start=" + start +
                ", end=" + end +
                ", icon=" + icon +
                ", color=" + color +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
