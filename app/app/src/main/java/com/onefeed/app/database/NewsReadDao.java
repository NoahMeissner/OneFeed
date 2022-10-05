package com.onefeed.app.database;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.onefeed.app.data.insight.NewsReadEntry;

import java.util.List;

@Dao
public interface NewsReadDao {
    @Query("SELECT * FROM news_read_list WHERE readDate = :todayDate")
    LiveData<List<NewsReadEntry>> getToday(Long todayDate);

    @Query("SELECT * FROM news_read_list WHERE readDate > :from AND readDate < :to")
    LiveData<List<NewsReadEntry>> getBetween(Long from, Long to);

    @Insert(onConflict = IGNORE)
    void insert(NewsReadEntry entry);
}
