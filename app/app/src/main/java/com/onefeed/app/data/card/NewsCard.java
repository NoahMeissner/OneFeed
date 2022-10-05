package com.onefeed.app.data.card;

import com.onefeed.app.data.feed.NewsSource;

import java.time.LocalDateTime;

public class NewsCard {
    private NewsSource source;
    private LocalDateTime publicationDate;
    private String webUrl;

    public NewsCard(NewsSource source, LocalDateTime publicationDate, String contentUrl) {
        this.source = source;
        this.publicationDate = publicationDate;
        this.webUrl = contentUrl;
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

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
