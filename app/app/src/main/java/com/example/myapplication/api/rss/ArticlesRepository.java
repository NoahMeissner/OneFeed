package com.example.myapplication.api.rss;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.card.ArticleCard;
import com.example.myapplication.data.feed.NewsSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;


public class ArticlesRepository {

    private final Executor executor;

    public ArticlesRepository(Executor executor) {
        this.executor = executor;
    }

    // Loads all articles for the specified rss urls by making multiple requests
    //   (one request per rss endpoint)
    public void loadArticlesForRssEndpoints(
            HashMap<Category.news, String> rssEndpoints,
            Context context,
            ArticleCardsCallback listener
    ) {
        executor.execute(() -> {
            ArrayList<ArticleCard> cards = new ArrayList<>();

            int currentIndex = 0;
            for (Map.Entry<Category.news, String> entry : rssEndpoints.entrySet()) {
                // Load all articles and notify listener when all data has been loaded
                boolean isFinalRun = currentIndex == rssEndpoints.entrySet().size() - 1;
                loadArticlesForRssEndpoint(
                        entry.getValue(),
                        entry.getKey(),
                        context,
                        articleResults -> {
                            cards.addAll(articleResults);
                            if (isFinalRun) {
                                listener.onComplete(cards);
                            }
                        });

                currentIndex++;
            }
        });
    }

    // Loads all articles for a single rss endpoint url
    public void loadArticlesForRssEndpoint(
            String url,
            Category.news category,
            Context context,
            ArticleCardsCallback listener) {
        executor.execute(() -> {
            RequestQueue queue = Volley.newRequestQueue(context);

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
            queue.add(rssRequest);
        });
    }

    private void createArticlesFromRss(
            String xmlResponse,
            Category.news newsCategory,
            Context context,
            ArticleInformationCallback listener
    ) {
        executor.execute(() -> {
            ArticleRssParser helper = new ArticleRssParser();

            // Parse rss response
            ArrayList<RssArticle> rssArticles = helper.parseArticles(xmlResponse, newsCategory);

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
                    CustomImageRequest imageRequest = new CustomImageRequest(
                            article.getImageUrl(),
                            context
                    );
                    imageRequest.run(image -> {
                        card.setImage(image);
                        // Callback if this is the final image to load
                        if (rssArticles.indexOf(article) == rssArticles.size() - 1) {
                            listener.onAllImagesLoaded(articleCards);
                        }
                    });
                }

                // Load source icon
                //   Volley uses caching by default for every request so we can run this for every
                //   article without performance loss
                CustomImageRequest sourceIconRequest = new CustomImageRequest(
                        article.getSourceIconUrl(),
                        context
                );
                sourceIconRequest.run(icon -> {
                    card.getSource().setIcon(icon);
                    // Callback if this is the final icon to load
                    notifyAllIconsLoadedIfFinal(listener, rssArticles, articleCards, article);
                });

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

    // Callback for loading multiple Article cards
    public interface ArticleCardsCallback {
        void onComplete(ArrayList<ArticleCard> articleResults);
    }

    // Callback for loading a single Article card
    public interface ArticleInformationCallback {
        // Includes all information which is included in the rss xml
        void onAllTextsLoaded(ArrayList<ArticleCard> articleResults);

        // Images are provided as urls in the rss xml so they have to be loaded seperately
        void onAllImagesLoaded(ArrayList<ArticleCard> articleResults);

        // Icons are provided as urls in the rss xml so they have to be loaded seperately
        void onAllIconsLoaded(ArrayList<ArticleCard> articleResults);
    }
}