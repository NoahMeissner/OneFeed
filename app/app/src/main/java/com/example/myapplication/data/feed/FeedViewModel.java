package com.example.myapplication.data.feed;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;
import com.example.myapplication.api.NewsRepository;
import com.example.myapplication.api.rss.RssUrls;
import com.example.myapplication.data.addSource.Constants;
import com.example.myapplication.data.addSource.SourceAdd;
import com.example.myapplication.data.card.NewsCard;
import com.example.myapplication.data.insight.InsightPreferencesHelper;
import com.example.myapplication.data.insight.NewsReadEntry;
import com.example.myapplication.database.DataBaseHelper;
import com.example.myapplication.database.InsightRepository;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class FeedViewModel extends AndroidViewModel {

    private InsightRepository insightRepository;
    private NewsRepository articlesRepository;
    private DataBaseHelper categoriesRepository;


    private final MutableLiveData<ArrayList<NewsCard>> newsCards;
    private final LiveData<List<NewsReadEntry>> newsReadList;
    private final LiveData<List<SourceAdd>> sources;
    private final MutableLiveData<Boolean> internetConnected;

    private final InsightPreferencesHelper insightPreferencesHelper;
    private boolean insightLimitIsReached = false;

    public FeedViewModel(Application application) {
        super(application);

        // Initialize
        this.insightRepository = new InsightRepository(application);
        this.articlesRepository = new NewsRepository(application);
        this.categoriesRepository = new DataBaseHelper(application);

        this.newsCards = new MutableLiveData<>(new ArrayList<NewsCard>() {});
        this.newsReadList = insightRepository.getNewsReadToday();
        this.sources = categoriesRepository.getSourceArrayList();
        this.internetConnected = new MutableLiveData<>(isOnline(application));

        this.insightPreferencesHelper = new InsightPreferencesHelper(application);

        // Initial load for cards
        this.loadNewsCards(application);
    }

    public MutableLiveData<ArrayList<NewsCard>> getNewsCards() {
        return newsCards;
    }

    public void loadNewsCards(Context context) {
        this.internetConnected.setValue(isOnline(context));
        if (this.internetConnected.getValue() && this.sources.getValue() != null) {
            boolean loadTwitter = false; // signals if twitter api should be requested
            List<Constants.interests> interestsList = new ArrayList<>();
            // Hashmap with all information required to load the news
            HashMap<Constants.news, List<String>> newsConfig = new HashMap<>();
            for (SourceAdd source: this.sources.getValue()) {
                switch (source.getCategories()) {
                    case Newspaper:
                        newsConfig.put(Constants.news.valueOf(source.getName()), new ArrayList<>());
                        break;
                    case Interests:
                        interestsList.add(Constants.interests.valueOf(source.getName()));
                        break;
                    case SocialMedia:
                        if (source.getName().equals("Twitter")) {
                            loadTwitter = source.isEnabled();
                        }
                        break;
                }
            }
            RssUrls rssUrls = new RssUrls();
            for (Constants.news news : newsConfig.keySet()) {
                newsConfig.put(news,rssUrls.getUrlsForNewspaper(news, interestsList));
            }

            articlesRepository.loadNews(newsConfig, loadTwitter, context, cards -> {
                setNewsCards(cards);
            });
        }

    }

    private HashMap<Constants.news, String> loadCategories(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources()
                .getString(R.string.initProcesBoolean), 0);
        Set<String> categories = sharedPreferences.getStringSet(Constants.Interests.name(), null);
        return null;
    }

    private void setNewsCards(ArrayList<NewsCard> articleResults) {
        ArrayList<NewsCard> newValues = new ArrayList<>();
        if (articleResults != null) {
            newValues.addAll(articleResults);

            // Sort by latest article
            newValues.sort(Comparator.comparing(NewsCard::getPublicationDate).reversed());

            newsCards.postValue(newValues);
        }
    }

    // https://stackoverflow.com/a/24692766
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void addReadArticle(String url) {
        insightRepository.addNewsReadForToday(url);
    }

    public void setLimitReached(Boolean isReached) {
        this.insightLimitIsReached = isReached;
    }

    public boolean getLimitIsEnabled(Context context) {
        return insightPreferencesHelper.getLimitationIsEnabled(context);
    }

    public boolean getIsLimitIsReached() {
        return this.insightLimitIsReached;
    }

    public int getAmountArticlesReadLimit(Context context) {
        return insightPreferencesHelper.getAmountArticlesPerDayLimit(context);
    }

    public LiveData<List<NewsReadEntry>> getNewsReadList() {
        return newsReadList;
    }

    public LiveData<List<SourceAdd>> getSources() {
        return sources;
    }

    public MutableLiveData<Boolean> getInternetConnected() {
        return internetConnected;
    }
}
