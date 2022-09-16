package com.example.myapplication.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.data.addSource.SourceAdd;

@Database(entities = {SourceAdd.class}, version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract  UserDao userDao();
}
