package com.example.myapplication.data.card;

import com.example.myapplication.data.feed.NewsSource;

import java.time.LocalDateTime;

public class TwitterCard extends NewsCard{
    private String content; // e.g. Today I found a new android trick!
    private String authorName; // e.g. Elon Musk
    private String authorHandle; // e.g. @elonmusk

    public TwitterCard(NewsSource source, LocalDateTime publicationDate, String content, String authorName, String authorHandle) {
        super(source, publicationDate);
        this.content = content;
        this.authorName = authorName;
        this.authorHandle = authorHandle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorHandle() {
        return authorHandle;
    }

    public void setAuthorHandle(String authorHandle) {
        this.authorHandle = authorHandle;
    }
}
