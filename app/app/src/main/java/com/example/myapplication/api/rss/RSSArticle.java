package com.example.myapplication.api.rss;

import android.graphics.Bitmap;

import com.example.myapplication.data.addSource.Category;

import java.time.LocalDateTime;

public class RSSArticle {

    private String title;
    private Category.news category;
    private String iconUrl;

    private LocalDateTime publicationDate;

    public RSSArticle(String title, Category.news category, String iconUrl, LocalDateTime publicationDate) {
        this.title = title;
        this.category = category;
        this.iconUrl = iconUrl;
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public Category.news getCategory() {
        return category;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }
}
