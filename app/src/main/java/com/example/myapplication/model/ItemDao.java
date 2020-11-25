package com.example.myapplication.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("select * from item where date = :today and isAvailable = :isAvailable")
    List<Item> getItemTodayList(String today, boolean isAvailable);

    @Query("select * from item where date = :today")
    List<Item> getItemTodayList(String today);

    @Query("select * from item where id = :id")
    Item getItemById(int id);

    @Query("select * from item where isAvailable = :isAvailable")
    List<Item> getItemAllList(boolean isAvailable);

    @Query("delete from item")
    void deleteAllItem();

    @Insert
    void insertItem(Item item);

    @Update
    void updateItem(Item item);

    @Delete
    void deleteItem(Item item);
}
