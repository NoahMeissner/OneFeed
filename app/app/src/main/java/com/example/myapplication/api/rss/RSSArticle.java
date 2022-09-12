package com.example.myapplication.api.rss;

import android.graphics.Bitmap;

import com.example.myapplication.data.addSource.Category;

public class RSSArticle {

    private String title;
    private Category.news category;
    private String iconUrl;
    private Bitmap bitmap;

    public RSSArticle(String title, Category.news category, String iconUrl){
        this.category = category;
        this.iconUrl = iconUrl;
        this.title = title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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
}
