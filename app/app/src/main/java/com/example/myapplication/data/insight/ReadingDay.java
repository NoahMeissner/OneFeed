package com.example.myapplication.data.insight;

import java.time.DayOfWeek;

public class ReadingDay {
    private DayOfWeek dayOfWeek;
    private int amountArticlesRead;

    public ReadingDay(DayOfWeek dayOfWeek, int amountArticlesRead) {
        this.dayOfWeek = dayOfWeek;
        this.amountArticlesRead = amountArticlesRead;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getAmountArticlesRead() {
        return amountArticlesRead;
    }

    public void setAmountArticlesRead(int amountArticlesRead) {
        this.amountArticlesRead = amountArticlesRead;
    }
}
