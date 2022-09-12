package com.example.myapplication.data.card;

import com.example.myapplication.data.feed.NewsSource;

import java.time.LocalDateTime;

public class NewsCard {
    private NewsSource source;
    private LocalDateTime publicationDate;

    public NewsCard(NewsSource source, LocalDateTime publicationDate) {
        this.source = source;
        this.publicationDate = publicationDate;
    }

    public NewsSource getSource() {
        return source;
    }

    public void setSource(NewsSource source) {
        this.source = source;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }
}
