package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.animation.addSource.OnSwipeTouchListener;
import com.example.myapplication.animation.addSource.Swipe;
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
//        initGestures();

        // Swipe to refresh
        this.refreshLayout = findViewById(R.id.feed_swipe_refresh);
        refreshLayout.setOnRefreshListener(() -> this.viewModel.loadNewsCards(this));

        // Open browser window in app on click
        this.adapter = new NewsCardListAdapter(url -> {
            if (viewModel.getLimitIsEnabled()) {
                if (viewModel.getIsLimitIsReached()) {
                    // Todo: generate string resource
                    Toast.makeText(
                            this,
                            "Sie haben Ihr tägliches Limit an News erreicht. Genießen Sie Ihre Zeit!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                viewModel.addReadArticle(url);
            }

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse(url));
        });

        // News cards recycler
        this.recycler = findViewById(R.id.recycler_news_cards);
        this.recycler.setLayoutManager(new LinearLayoutManager(this));
        this.recycler.setAdapter(this.adapter);
        this.recycler.setItemAnimator(null); // Disable animation

        // Cards listeners
        this.viewModel.getNewsCards().observe(this, newsCards -> {
            adapter.updateItems(newsCards);
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        });

        this.viewModel.getNewsReadList().observe(this, l -> {
            viewModel.setLimitReached(l.size() >= viewModel.getAmountArticlesReadLimit(this));
            Log.d("TAG", "onCreate: read: " + l.size() + " of " +
                    viewModel.getAmountArticlesReadLimit(this) + " articles.");
        });
    }

//    private void initGestures() {
//        /*
//        This Method will initial the Swipe Gestures
//         */
//        View appBar = findViewById(R.id.component_app_bar_id);
//        View activity = findViewById(R.id.recycler_news_cards);
//        setSwipeListener(appBar);
//        setSwipeListener(activity);
//    }

    private void setSwipeListener(View view){
        OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(
                view, swipe -> {
            if (swipe== Swipe.Right){
                Intent intent = new Intent(this, InsightActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
            if (swipe == Swipe.Left){
                Intent intent = new Intent(this, AddSourceActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    private void initializeNavigationButtons() {
        this.sourcesNavigationButton = findViewById(R.id.sources_icon);
        this.sourcesNavigationButton.setOnClickListener(l -> {
            Intent intent = new Intent(this, AddSourceActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        });

        this.insightNavigationButton = findViewById(R.id.insight_icon);
        this.insightNavigationButton.setOnClickListener(l -> {
            Intent intent = new Intent(this, InsightActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
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
