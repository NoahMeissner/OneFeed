package com.example.myapplication.data.card;

import com.example.myapplication.data.NewsSource;

import java.time.LocalDateTime;

public class ArticleCard extends NewsCard{
    private String title;

    public ArticleCard(String title, NewsSource source, LocalDateTime publicationDate) {
        super(source, publicationDate);

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
