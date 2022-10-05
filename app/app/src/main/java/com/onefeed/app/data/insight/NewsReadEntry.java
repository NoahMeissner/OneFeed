package com.onefeed.app.data.insight;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.time.LocalDate;

@Entity(primaryKeys = {"url", "readDate"}, tableName = "news_read_list")
public class NewsReadEntry {
    @NonNull
    private String url;
    @NonNull
    private Long readDate;

    public NewsReadEntry(String url) {
        this.url = url;
        this.readDate = LocalDate.now().toEpochDay();
    }

    public String getUrl() {
        return url;
    }

    public Long getReadDate() {
        return readDate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setReadDate(Long readDate) {
        this.readDate = readDate;
    }
}
