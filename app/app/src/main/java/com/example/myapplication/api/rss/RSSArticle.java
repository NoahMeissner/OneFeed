package com.example.myapplication.api.rss;

import com.example.myapplication.data.addSource.Category;

import java.time.LocalDateTime;

public class RSSArticle {

    private String title;
    private Category.news category;
    private String imageUrl;
    private LocalDateTime publicationDate;
    private String sourceName;
    private String sourceIconUrl;
    private String webUrl;

    public RSSArticle(String title, Category.news category, String imageUrl, LocalDateTime publicationDate, String sourceName, String sourceIconUrl, String webUrl) {
        this.title = title;
        this.category = category;
        this.imageUrl = imageUrl;
        this.publicationDate = publicationDate;
        this.sourceName = sourceName;
        this.sourceIconUrl = sourceIconUrl;
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public Category.news getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceIconUrl() {
        return sourceIconUrl;
    }

    public void setSourceIconUrl(String sourceIconUrl) {
        this.sourceIconUrl = sourceIconUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
