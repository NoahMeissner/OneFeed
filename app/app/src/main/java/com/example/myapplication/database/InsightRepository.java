package com.example.myapplication.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.myapplication.data.insight.NewsReadEntry;

import java.time.LocalDate;
import java.util.List;

public class InsightRepository {
    private NewsReadDao newsReadDao;
    private LiveData<List<NewsReadEntry>> newsReadToday;
    private LiveData<List<NewsReadEntry>> newsReadCurrentYear;


    public InsightRepository(Application application) {
        this.newsReadDao = AppDataBase.getDatabase(application).newsReadDao();
        this.newsReadToday = this.newsReadDao.getToday(LocalDate.now().toEpochDay());
        this.newsReadCurrentYear = this.newsReadDao.getBetween(
                LocalDate.of(LocalDate.now().getYear(), 1, 1).toEpochDay(),
                LocalDate.of(LocalDate.now().getYear() + 1, 1, 1).toEpochDay());
    }

    public LiveData<List<NewsReadEntry>> getNewsReadToday() {
        return newsReadToday;
    }

    public LiveData<List<NewsReadEntry>> getNewsCurrentYear() {
        return newsReadCurrentYear;
    }

    public void addNewsReadForToday(String url) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            this.newsReadDao.insert(new NewsReadEntry(url));
        });
    }
}
