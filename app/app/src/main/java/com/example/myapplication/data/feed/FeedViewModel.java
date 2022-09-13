package com.example.myapplication.data.feed;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.api.rss.RSSApiRequest;
import com.example.myapplication.api.rss.RSSUrls;
import com.example.myapplication.data.addSource.Category;
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
        rssApiRequest.loadArticlesForCategories(corona, context, articleResults -> {
            ArrayList<NewsCard> newValues = newsCards.getValue();
            if (newValues != null) {
                newValues.addAll(articleResults);

                // Sort by latest article
                newValues.sort(Comparator.comparing(NewsCard::getPublicationDate).reversed());

                this.newsCards.setValue(newValues);
            }
        });
    }
}
