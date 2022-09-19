package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.data.feed.FeedViewModel;
import com.example.myapplication.adapter.NewsCardListAdapter;

public class FeedActivity extends AppCompatActivity {

    private FeedViewModel viewModel;
    SwipeRefreshLayout refreshLayout;
    private NewsCardListAdapter adapter;
    private RecyclerView recycler;
    private ImageButton sourcesNavigationButton;
    private ImageButton insightNavigationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // ViewModel
        this.viewModel = new ViewModelProvider(this).get(FeedViewModel.class);

        // Title-bar
        setSupportActionBar(findViewById(R.id.toolbar_collapse));
        initializeNavigationButtons();

        // Swipe to refresh
        this.refreshLayout = findViewById(R.id.feed_swipe_refresh);
        refreshLayout.setOnRefreshListener(() -> this.viewModel.loadNewsCards(this));

        // News cards recycler
        this.adapter = new NewsCardListAdapter();
        this.recycler = findViewById(R.id.recycler_news_cards);
        this.recycler.setLayoutManager(new LinearLayoutManager(this));
        this.recycler.setAdapter(this.adapter);
        this.recycler.setItemAnimator(null); // Disable animation

        // Cards listeners
        this.viewModel.getNewsCards().observe(this, newsCards -> {
            adapter.updateItems(newsCards);
            // Todo: fix diff
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty, menu);
        return true;
    }

//    private void setupDummyCards() {
//        NewsSource sampleArticleSource = new NewsSource(
//                "Spiegel", "https://www.spiegel.de/"
//        );
//        NewsSource sampleTwitterSource = new NewsSource(
//                "Twitter", "https://twitter.com/"
//        );
//        ArticleCard sampleArticleCard = new ArticleCard(
//                getString(R.string.lorem_ipsum), sampleArticleSource, LocalDateTime.now(), null
//        );
//        TwitterCard sampleTwitterCard = new TwitterCard(
//            sampleTwitterSource, LocalDateTime.now(), getString(R.string.lorem_ipsum_long),
//                "Elon Musk", "@elonmusk"
//        );
//        ArrayList<NewsCard> sampleCards = new ArrayList<>(Arrays.asList(
//                sampleArticleCard, sampleTwitterCard, sampleArticleCard, sampleArticleCard,
//                sampleArticleCard, sampleTwitterCard, sampleArticleCard, sampleArticleCard,
//                sampleArticleCard, sampleTwitterCard, sampleArticleCard, sampleArticleCard
//                ));
//        this.adapter.updateItems(sampleCards);
//    }

}
