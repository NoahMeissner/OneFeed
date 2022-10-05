package com.onefeed.app.api;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onefeed.app.api.rss.RssArticle;
import com.onefeed.app.api.rss.RssArticleParser;
import com.onefeed.app.api.twitter.TwitterApiHelper;
import com.onefeed.app.data.addSource.Constants;
import com.onefeed.app.data.card.ArticleCard;
import com.onefeed.app.data.card.NewsCard;
import com.onefeed.app.data.card.TwitterCard;
import com.onefeed.app.data.feed.NewsSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NewsRepository {

    private ExecutorService executor;
    private final RequestQueue requestQueue;
    private final TwitterApiHelper twitterApi;

    public NewsRepository(Context context) {
        this.twitterApi = new TwitterApiHelper(context);
        this.requestQueue = Volley.newRequestQueue(context);
    }

    // Loads all articles for the specified rss urls by making multiple requests
    //   (one request per rss endpoint)
    public void loadNews(
            HashMap<Constants.news, List<String>> rssEndpoints,
            boolean loadTwitter,
            Context context,
            NewsCardsCallback listener
    ) {
        this.requestQueue.start();
        this.executor = Executors.newFixedThreadPool(2);

        executor.execute(() -> {
            ArrayList<NewsCard> cards = new ArrayList<>();

            loadNews(rssEndpoints, loadTwitter, context, new LoadNewsCallback() {
                boolean rssComplete = false;
                boolean twitterComplete = false;

                @Override
                public void onRssComplete(ArrayList<ArticleCard> articleResults) {
                    cards.addAll(articleResults);
                    rssComplete = true;
                    Log.d(TAG, "onRssComplete: loaded rss cards");

                    returnResultIfComplete();
                }

                @Override
                public void onRssFailed() {
                    rssComplete = true;
                    Log.d(TAG, "onRssComplete: could not load any newspaper articles.");
                    returnResultIfComplete();
                }

                @Override
                public void onTwitterComplete(ArrayList<TwitterCard> tweetResults) {
                    cards.addAll(tweetResults);
                    twitterComplete = true;
                    Log.d(TAG, "onTwitterComplete: loaded twitter cards: " + tweetResults.stream().count() + " cards.");

                    returnResultIfComplete();
                }

                @Override
                public void onTwitterFailed() {
                    twitterComplete = true;
                    Log.d(TAG, "" +
                            "onTwitterComplete: Could not load twitter cards." +
                            "This is probably and authentication issue " +
                            "or the user disabled this feature in settings.");

                    returnResultIfComplete();
                }

                private void returnResultIfComplete() {
                    if (rssComplete && twitterComplete) {
                        requestQueue.stop();
                        executor.shutdown();

                        listener.onComplete(cards);
                    }
                }
            });
        });
    }

    private void loadNews(HashMap<Constants.news, List<String>> rssEndpoints, boolean loadTwitter, Context context, LoadNewsCallback listener) {
        // Load rss articles for all categories
        loadArticles(rssEndpoints, context, listener);

        // Load all tweets
        if (loadTwitter) {
            twitterApi.loadTweets(context, requestQueue, listener);
        } else {
            Log.d(TAG,
                    "loadNews: did not load twitter because the user has disabled this feature.");
            listener.onTwitterFailed();
        }
    }

    private void loadArticles(HashMap<Constants.news, List<String>> rssEndpoints, Context context, LoadNewsCallback listener) {
        ArrayList<ArticleCard> articleCards = new ArrayList<>();

        int newsIndex = 0;
        for (Map.Entry<Constants.news, List<String>> entry : rssEndpoints.entrySet()) {
            int urlIndex = 0;
            for (String url : entry.getValue()) {
                // Load all articles and notify listener when all data has been loaded
                boolean isFinalRun = urlIndex == rssEndpoints.entrySet().size() - 1
                        && newsIndex == rssEndpoints.entrySet().size() - 1;
                loadArticlesForRssEndpoint(
                    url,
                    entry.getKey(),
                    context,
                    articleResults -> {
                        articleCards.addAll(articleResults);

                        if (isFinalRun) {
                            listener.onRssComplete(articleCards);
                        }
                });

                urlIndex++;
            }
            newsIndex++;
        }

        // Logging if no articles have been loaded
        if (newsIndex == 0) {
            listener.onRssFailed();
            Log.d(TAG, "loadArticles: No news have been loaded. " +
                    "This is because the user has specified no interests.");
        }
    }

    // Loads all articles for a single rss endpoint url
    public void loadArticlesForRssEndpoint(
            String url,
            Constants.news category,
            Context context,
            ArticleCardsCallback listener) {
        executor.execute(() -> {
            // Loading all articles for an rss endpoint involves the following
            //    1. load the rss xml
            //    2. load and set the thumbnail images for all articles
            //    3. load and set the icon for the source
            StringRequest rssRequest = new StringRequest(url, response -> createArticlesFromRss(
                    response,
                    category,
                    context,
                    new ArticleInformationCallback() {
                        boolean titlesLoaded = false;
                        boolean imagesLoaded = false;
                        boolean iconsLoaded = false;

                        @Override
                        public void onAllTextsLoaded(ArrayList<ArticleCard> articleResults) {
                            titlesLoaded = true;
                            notifyListenerIfAllLoaded(articleResults);
                        }

                        @Override
                        public void onAllImagesLoaded(ArrayList<ArticleCard> articleResults) {
                            imagesLoaded = true;
                            notifyListenerIfAllLoaded(articleResults);
                        }

                        @Override
                        public void onAllIconsLoaded(ArrayList<ArticleCard> articleResults) {
                            iconsLoaded = true;
                            notifyListenerIfAllLoaded(articleResults);
                        }

                        // Notifies the listener, if all data for all cards is loaded
                        private void notifyListenerIfAllLoaded(
                                ArrayList<ArticleCard> articleResults
                        ) {
                            if (titlesLoaded && imagesLoaded && iconsLoaded) {
                                listener.onComplete(articleResults);
                            }
                        }
                    }),
                    error -> Log.d("Error", "Erros") // Todo: Handle errors
            );
            requestQueue.add(rssRequest);
        });
    }

    private void createArticlesFromRss(
            String xmlResponse,
            Constants.news newsConstants,
            Context context,
            ArticleInformationCallback listener
    ) {
        executor.execute(() -> {
            RssArticleParser helper = new RssArticleParser();

            // Parse rss response
            ArrayList<RssArticle> rssArticles = helper.parseArticles(xmlResponse, newsConstants);

            // Create cards out of the rss response
            //   loads images via specified urls
            //   loads icons via specified urls
            ArrayList<ArticleCard> articleCards = new ArrayList<>();
            for (RssArticle article : rssArticles) {
                ArticleCard card = new ArticleCard(
                        article.getTitle(),
                        new NewsSource(article.getSourceName()),
                        article.getPublicationDate(),
                        article.getWebUrl()
                );

                // Load article image
                //   Volley uses caching by default for every request so we can run this for every
                //   article without performance loss
                if (!article.getImageUrl().equals("")) {
                    CustomImageRequest imageRequest = new CustomImageRequest(article.getImageUrl());
                    imageRequest.run(image -> {
                        card.setImage(image);
                        // Callback if this is the final image to load
                        if (rssArticles.indexOf(article) == rssArticles.size() - 1) {
                            listener.onAllImagesLoaded(articleCards);
                        }
                    }, requestQueue, context);
                }

                // Load source icon
                //   Volley uses caching by default for every request so we can run this for every
                //   article without performance loss
                CustomImageRequest sourceIconRequest = new CustomImageRequest(article.getSourceIconUrl());
                sourceIconRequest.run(icon -> {
                    card.getSource().setIcon(icon);
                    // Callback if this is the final icon to load
                    notifyAllIconsLoadedIfFinal(listener, rssArticles, articleCards, article);
                }, requestQueue, context);

                articleCards.add(card);
            }

            listener.onAllTextsLoaded(articleCards);
        });
    }

    private void notifyAllIconsLoadedIfFinal(
            ArticleInformationCallback listener,
            ArrayList<RssArticle> rssArticles,
            ArrayList<ArticleCard> articleCards,
            RssArticle article
    ) {
        if (rssArticles.indexOf(article) == rssArticles.size() - 1) {
            listener.onAllIconsLoaded(articleCards);
        }
    }

    // Callback for loading tweets
    public interface NewsCardsCallback {
        void onComplete(ArrayList<NewsCard> cardResults);
    }

    // Callback for loading tweets and articles
    public interface LoadNewsCallback {
        void onRssComplete(ArrayList<ArticleCard> articleResults);
        void onRssFailed();
        void onTwitterComplete(ArrayList<TwitterCard> tweetResults);
        void onTwitterFailed();
    }

    // Callback for loading multiple Article cards
    public interface ArticleCardsCallback {
        void onComplete(ArrayList<ArticleCard> articleCardsResults);
    }

    // Callback for loading a single Article card
    public interface ArticleInformationCallback {
        // Includes all information which is included in the rss xml
        void onAllTextsLoaded(ArrayList<ArticleCard> articleResults);

        // Images are provided as urls in the rss xml so they have to be loaded separately
        void onAllImagesLoaded(ArrayList<ArticleCard> articleResults);

        // Icons are provided as urls in the rss xml so they have to be loaded separately
        void onAllIconsLoaded(ArrayList<ArticleCard> articleResults);
    }
}