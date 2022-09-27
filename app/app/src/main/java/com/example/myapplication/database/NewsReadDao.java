package com.example.myapplication.database;

import static androidx.room.OnConflictStrategy.ABORT;
import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.data.insight.NewsReadEntry;

import java.util.List;

@Dao
public interface NewsReadDao {
    @Query("SELECT * FROM news_read_list WHERE readDate = :todayDate")
    LiveData<List<NewsReadEntry>> getToday(Long todayDate);

    @Insert(onConflict = IGNORE)
    void insert(NewsReadEntry entry);
}
