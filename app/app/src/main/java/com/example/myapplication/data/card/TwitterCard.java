package com.example.myapplication.data.card;

import android.graphics.Bitmap;

import com.example.myapplication.data.feed.NewsSource;

import java.time.LocalDateTime;

public class TwitterCard extends NewsCard{
    private String content; // e.g. Today I found a new android trick!
    private String authorName; // e.g. Elon Musk
    private String authorUsername; // e.g. @elonmusk
    private Bitmap authorProfileImage;

    public TwitterCard(NewsSource source, LocalDateTime publicationDate, String contentUrl, String content, String authorName, String authorUsername, Bitmap authorProfileImage) {
        super(source, publicationDate, contentUrl);
        this.content = content;
        this.authorName = authorName;
        this.authorUsername = authorUsername;
        this.authorProfileImage = authorProfileImage;
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

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public Bitmap getAuthorProfileImage() {
        return authorProfileImage;
    }

    public void setAuthorProfileImage(Bitmap authorProfileImage) {
        this.authorProfileImage = authorProfileImage;
    }
}
