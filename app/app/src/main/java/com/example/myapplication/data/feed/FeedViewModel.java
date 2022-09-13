package com.example.myapplication.data.feed;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.rss.RSSApiRequest;
import com.example.myapplication.api.rss.RSSUrls;
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.card.NewsCard;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<NewsCard>> newsCards;

    public FeedViewModel(Application application) {
        super(application);

        this.newsCards = new MutableLiveData<>(new ArrayList<NewsCard>() {});

        RSSUrls rssUrls = new RSSUrls();
        HashMap<Category.news,String> corona = rssUrls.getCategory(Category.interests.Politik);

        RSSApiRequest rssApiRequest = new RSSApiRequest();
        rssApiRequest.makeRSSCategory(corona, application, articleResults -> {
            setNewsCards(articleResults);
        });
    }

    public MutableLiveData<ArrayList<NewsCard>> getNewsCards() {
        return newsCards;
    }

    public void setNewsCards(ArrayList<NewsCard> newsCards) {
        this.newsCards.setValue(newsCards);
    }
}
