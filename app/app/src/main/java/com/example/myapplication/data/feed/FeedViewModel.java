package com.example.myapplication.data.feed;

import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;
import com.example.myapplication.api.rss.RSSApiRequest;
import com.example.myapplication.api.rss.RSSUrls;
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.card.ArticleCard;
import com.example.myapplication.data.card.NewsCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class FeedViewModel extends AndroidViewModel {

    private final MutableLiveData<ArrayList<NewsCard>> newsCards;

    public FeedViewModel(Application application) {
        super(application);

        // Initialize
        this.newsCards = new MutableLiveData<>(new ArrayList<NewsCard>() {});

        // Initial load
        this.loadNewsCards(application);
    }

    public MutableLiveData<ArrayList<NewsCard>> getNewsCards() {
        return newsCards;
    }

    public void loadNewsCards(Context context) {
        // Todo: Use categories provided by user preferences
        RSSUrls rssUrls = new RSSUrls();
        HashMap<Category.news, String> corona = rssUrls.getCategory(Category.interests.Politik);

        RSSApiRequest rssApiRequest = new RSSApiRequest();
        rssApiRequest.loadArticlesForCategories(corona, context, new RSSApiRequest.OnResult() {
            @Override
            public void titlesLoaded(ArrayList<NewsCard> cards) {
                setLoadingImages(cards, context);
                setNewsCards(cards);
            }

            @Override
            public void iconsLoaded(ArrayList<NewsCard> cards) {
                setLoadingImages(cards, context);
                setNewsCards(cards);
            }

            @Override
            public void imagesLoaded(ArrayList<NewsCard> cards) {
                setLoadingImages(cards, context);
                setNewsCards(cards);
            }
        });
    }

    // Sets a plain color as the content of the images and icons while loading
    // https://blog.prototypr.io/skeleton-loader-an-overview-purpose-usage-and-design-173b5340d0e1
    private void setLoadingImages(ArrayList<NewsCard> cards, Context context) {
        Bitmap loadingImage = generateLoadingImage(context);
        for (NewsCard card : cards) {
            if (card.getClass() == ArticleCard.class) {
                if (((ArticleCard) card).getImage() == null) {
                    ((ArticleCard) card).setImage(loadingImage);
                }
                if (card.getSource().getIcon() == null) {
                    ((ArticleCard) card).getSource().setIcon(loadingImage);
                }
            }
        }
    }

    @NonNull
    private Bitmap generateLoadingImage(Context context) {
        Bitmap loadingImage = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                R.style.AppTheme, new int[] {com.google.android.material.R.attr.colorSecondaryContainer}
        );
        int loadingImageColorId = a.getResourceId(0, 0);
        loadingImage.eraseColor(context.getColor(loadingImageColorId));
        return loadingImage;
    }

    private void setNewsCards(ArrayList<NewsCard> articleResults) {
        ArrayList<NewsCard> newValues = new ArrayList<>();
        if (articleResults != null) {
            newValues.addAll(articleResults);

            // Sort by latest article
            newValues.sort(Comparator.comparing(NewsCard::getPublicationDate).reversed());

            newsCards.setValue(newValues);
        }
    }
}
