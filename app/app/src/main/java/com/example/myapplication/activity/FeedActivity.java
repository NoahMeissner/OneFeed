package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.api.rss.RSSApiRequest;
import com.example.myapplication.api.rss.RSSArticle;
import com.example.myapplication.api.rss.RSSUrls;
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.card.ArticleCard;
import com.example.myapplication.data.feed.NewsSource;
import com.example.myapplication.data.card.NewsCard;
import com.example.myapplication.data.card.TwitterCard;
import com.example.myapplication.adapter.NewsCardListAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FeedActivity extends AppCompatActivity {

    private NewsCardListAdapter adapter;
    private RecyclerView recycler;
    private ImageButton sourcesNavigationButton;
    private ImageButton insightNavigationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Title-bar
        setSupportActionBar(findViewById(R.id.toolbar_collapse));

        initializeNavigationButtons();

        // News cards recycler
        this.adapter = new NewsCardListAdapter();
        this.recycler = findViewById(R.id.recycler_news_cards);
        this.recycler.setLayoutManager(new LinearLayoutManager(this));
        this.recycler.setAdapter(this.adapter);

        // Dummy data setup
//        setupDummyCards();
        initApi();
        //@TODO Löschen
        killPreferences();
    }

    private void killPreferences() {
        SharedPreferences pref = getSharedPreferences(getResources()
                .getString(R.string.initProcesBoolean), 0);

        SharedPreferences.Editor editPreferences = pref.edit();
        editPreferences.clear();
        editPreferences.commit();
    }

    private void initializeNavigationButtons() {
        this.sourcesNavigationButton = findViewById(R.id.sources_icon);
        this.sourcesNavigationButton.setOnClickListener(l -> {
            Intent intent = new Intent(this, AddSourceActivity.class);
            startActivity(intent);
        });

        this.insightNavigationButton = findViewById(R.id.insight_icon);
        this.insightNavigationButton.setOnClickListener(l -> {
            Intent intent = new Intent(this, InsightActivity.class);
            startActivity(intent);
        });
    }

    private void setupDummyCards() {
        NewsSource sampleArticleSource = new NewsSource(
                "Spiegel", "https://www.spiegel.de/"
        );
        NewsSource sampleTwitterSource = new NewsSource(
                "Twitter", "https://twitter.com/"
        );
        ArticleCard sampleArticleCard = new ArticleCard(
                getString(R.string.lorem_ipsum), sampleArticleSource, LocalDateTime.now(), null
        );
        TwitterCard sampleTwitterCard = new TwitterCard(
            sampleTwitterSource, LocalDateTime.now(), getString(R.string.lorem_ipsum_long),
                "Elon Musk", "@elonmusk"
        );
        ArrayList<NewsCard> sampleCards = new ArrayList<>(Arrays.asList(
                sampleArticleCard, sampleTwitterCard, sampleArticleCard, sampleArticleCard,
                sampleArticleCard, sampleTwitterCard, sampleArticleCard, sampleArticleCard,
                sampleArticleCard, sampleTwitterCard, sampleArticleCard, sampleArticleCard
                ));
        this.adapter.updateItems(sampleCards);
    }


    private void initApi() {
        HashMap<Category,String> url = new HashMap<>();
        RSSUrls rssUrls = new RSSUrls();
        HashMap<Category.news,String> corona = rssUrls.getCategory(Category.interests.Politik);

        RSSApiRequest rssApiRequest = new RSSApiRequest();
        rssApiRequest.makeRSSCategory(corona, this, new RSSApiRequest.onResult() {
            @Override
            public void result(ArrayList<RSSArticle> rssArticles) {
                NewsSource sampleArticleSource = new NewsSource(
                        "Spiegel", "https://www.spiegel.de/"
                );

                ArrayList<NewsCard> cards = new ArrayList<>();
                for (RSSArticle rssArticle : rssArticles) {
                    NewsCard card = new ArticleCard(
                            rssArticle.getTitle(),
                            sampleArticleSource,
                            LocalDateTime.now(),
                            rssArticle.getBitmap()
                    );
                    cards.add(card);
                }
                adapter.updateItems(cards);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty, menu);
        return true;
    }
}