package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.data.NewsCard;
import com.example.myapplication.data.NewsSource;
import com.example.myapplication.ui.NewsCardListAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class FeedActivity extends AppCompatActivity {

    private NewsCardListAdapter adapter;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Title-bar
        setSupportActionBar(findViewById(R.id.toolbar));

        // News cards recycler
        this.adapter = new NewsCardListAdapter();
        this.recycler = findViewById(R.id.recycler_news_cards);
        this.recycler.setLayoutManager(new LinearLayoutManager(this));
        this.recycler.setAdapter(this.adapter);

        // Dummy data setup
        NewsSource sampleSource = new NewsSource(
                "Spiegel", "https://www.spiegel.de/"
        );
        NewsCard sampleCard = new NewsCard(
                getString(R.string.sample_article_title), sampleSource
        );
        ArrayList<NewsCard> sampleCards = new ArrayList<>(Arrays.asList(
                sampleCard, sampleCard, sampleCard, sampleCard, sampleCard, sampleCard,
                sampleCard, sampleCard, sampleCard, sampleCard, sampleCard, sampleCard,
                sampleCard, sampleCard, sampleCard, sampleCard, sampleCard, sampleCard
        ));
        this.adapter.updateItems(sampleCards);
    }
}