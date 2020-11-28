package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "item")
public class Item {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "start")
    private String start;
    @ColumnInfo(name = "end")
    private String end;
    @ColumnInfo(name = "icon")
    private int icon;
    @ColumnInfo(name = "color")
    private int color;
    @ColumnInfo(name = "comment")
    private String comment;
    @ColumnInfo(name = "isSuccess")
    private boolean isSuccess;
    @ColumnInfo(name = "isAvailable")
    private boolean isAvailable;
    @ColumnInfo(name = "isNoti")
    private boolean isNoti;

    public Item() { }

    @Ignore
    public Item(String name, String date, int icon, int color) {
        this.name = name;
        this.date = date;
        this.icon = icon;
        this.color = color;
        isAvailable = true;
        isNoti = true;
    }

    public String getTime() {
        if (start == null) {
            return "";
        }
        if (end == null) {
            return start;
        } return start + " - " + end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isNoti() {
        return isNoti;
    }

    public void setNoti(boolean noti) {
        isNoti = noti;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", icon=" + icon +
                ", color=" + color +
                ", comment='" + comment + '\'' +
                ", isSuccess=" + isSuccess +
                ", isAvailable=" + isAvailable +
                ", isNoti=" + isNoti +
                '}';
    }
}
