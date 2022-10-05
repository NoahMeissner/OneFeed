package com.onefeed.app.api.rss;

import com.onefeed.app.data.addSource.Constants;

import java.time.LocalDateTime;

public class RssArticle {

    private String title;
    private Constants.news category;
    private String imageUrl;
    private LocalDateTime publicationDate;
    private String sourceName;
    private String sourceIconUrl;
    private String webUrl;

    public RssArticle(String title, Constants.news category, String imageUrl, LocalDateTime publicationDate, String sourceName, String sourceIconUrl, String webUrl) {
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

    public Constants.news getCategory() {
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
