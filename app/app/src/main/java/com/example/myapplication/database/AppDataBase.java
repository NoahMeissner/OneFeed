package com.example.myapplication.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.data.addSource.SourceAdd;
import com.example.myapplication.data.insight.NewsReadEntry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {SourceAdd.class, NewsReadEntry.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract NewsReadDao newsReadDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile AppDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDataBase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
