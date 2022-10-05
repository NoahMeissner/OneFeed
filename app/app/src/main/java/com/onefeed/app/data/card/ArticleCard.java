package com.onefeed.app.data.card;

import android.graphics.Bitmap;

import com.onefeed.app.data.feed.NewsSource;

import java.time.LocalDateTime;

public class ArticleCard extends NewsCard{
    private String title;
    private Bitmap image;

    public ArticleCard(
            String title,
            NewsSource source,
            LocalDateTime publicationDate,
            String webUrl
    ) {
        super(source, publicationDate, webUrl);

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
