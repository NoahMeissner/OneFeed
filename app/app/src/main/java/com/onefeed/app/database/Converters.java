package com.onefeed.app.database;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class Converters {
    @TypeConverter
    public LocalDate fromTimestamp(Long value) {
        return LocalDate.ofEpochDay(value);
    }

    @TypeConverter
    public Long fromLocalDate(LocalDate localDate) {
        return localDate.toEpochDay();
    }
}
