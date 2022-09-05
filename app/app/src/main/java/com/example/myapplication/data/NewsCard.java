package com.example.myapplication.data;

public class NewsCard {
    private String title;
    private NewsSource source;

    public NewsCard(String title, NewsSource source) {
        this.title = title;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NewsSource getSource() {
        return source;
    }

    public void setSource(NewsSource source) {
        this.source = source;
    }
}
