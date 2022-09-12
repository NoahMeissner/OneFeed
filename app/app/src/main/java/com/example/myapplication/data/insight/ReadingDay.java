package com.example.myapplication.data.insight;

public class ReadingDay {
    private int dayOfYear;
    private int amountArticlesRead;

    public ReadingDay(int dayOfYear, int amountArticlesRead) {
        this.dayOfYear = dayOfYear;
        this.amountArticlesRead = amountArticlesRead;
    }

    public int getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(int dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public int getAmountArticlesRead() {
        return amountArticlesRead;
    }

    public void setAmountArticlesRead(int amountArticlesRead) {
        this.amountArticlesRead = amountArticlesRead;
    }
}
