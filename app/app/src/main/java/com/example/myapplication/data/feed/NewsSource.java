package com.example.myapplication.data.feed;

import android.graphics.Bitmap;

public class NewsSource {
    private String name;
    private Bitmap icon;

    public NewsSource(String name, Bitmap iconUrl) {
        this.name = name;
        this.icon = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}
