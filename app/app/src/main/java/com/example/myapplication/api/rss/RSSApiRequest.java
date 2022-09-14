package com.example.myapplication.api.rss;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.card.ArticleCard;
import com.example.myapplication.data.card.NewsCard;
import com.example.myapplication.data.feed.NewsSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;


public class RSSApiRequest {

    private final Executor executor;

    public RSSApiRequest(Executor executor) {
        this.executor = executor;
    }

    public interface ArticleCardsCallback {
        void onComplete(ArrayList<NewsCard> articleResults);
    }

    public interface ArticleInformationCallback {
        void onAllTitlesLoaded(ArrayList<NewsCard> articleResults);
        void onAllImagesLoaded(ArrayList<NewsCard> articleResults);
        void onAllIconsLoaded(ArrayList<NewsCard> articleResults);

    }

    public void loadArticlesForCategories(
            HashMap<Category.news, String> url,
            Context context,
            ArticleCardsCallback listener
    ) {
        executor.execute(() -> {
            ArrayList<NewsCard> cards = new ArrayList<>();

            int currentIndex = 0;
            for (Map.Entry<Category.news, String> entry : url.entrySet()) {
                boolean isFinalRun = currentIndex == url.entrySet().size() - 1;

                loadArticlesForCategory(entry.getValue(), entry.getKey(), context, new ArticleCardsCallback() {
                    @Override
                    public void onComplete(ArrayList<NewsCard> articleResults) {
                        cards.addAll(articleResults);

                        if (isFinalRun) {
                            listener.onComplete(cards);
                        }
                    }
                });

                currentIndex++;
            }
        });

    }

    private void loadArticlesForCategory(
            String url,
            Category.news category,
            Context context,
            ArticleCardsCallback listener) {
        executor.execute(() -> {
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest jsonObjectRequest = new StringRequest(
                    url,
                    response -> createArticles(response, category, context, new ArticleInformationCallback() {
                        boolean titlesLoaded = false;
                        boolean imagesLoaded = false;
                        boolean iconsLoaded = false;

                        @Override
                        public void onAllTitlesLoaded(ArrayList<NewsCard> articleResults) {
                            titlesLoaded = true;
                            notifyListenerIfComplete(articleResults);
                        }

                        @Override
                        public void onAllImagesLoaded(ArrayList<NewsCard> articleResults) {
                            imagesLoaded = true;
                            notifyListenerIfComplete(articleResults);
                        }

                        @Override
                        public void onAllIconsLoaded(ArrayList<NewsCard> articleResults) {
                            iconsLoaded = true;
                            notifyListenerIfComplete(articleResults);
                        }

                        private void notifyListenerIfComplete(ArrayList<NewsCard> articleResults) {
                            if (titlesLoaded && imagesLoaded && iconsLoaded) {
                                listener.onComplete(articleResults);
                            }
                        }
                    }),
                    error -> Log.d("Error", "Erros")
            );
            queue.add(jsonObjectRequest);
        });
    }

    private void createArticles(
            String xmlFile,
            Category.news newsCategory,
            Context context,
            ArticleInformationCallback listener
    ) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                RSSHelper helper = new RSSHelper();
                ArrayList<RSSArticle> articles = helper.createArticlesFromResponse(
                        xmlFile,
                        newsCategory
                );

                ArrayList<NewsCard> articleCards = new ArrayList<>();
                for (RSSArticle article : articles) {
                    ArticleCard card = new ArticleCard(
                            article.getTitle(),
                            new NewsSource(article.getSourceName()),
                            article.getPublicationDate(),
                            article.getWebUrl()
                    );

                    if (!article.getImageUrl().equals("")) {
                        CustomImageRequest imageRequest = new CustomImageRequest(article.getImageUrl(), context);
                        imageRequest.run(image -> {
                            card.setImage(image);
                            // Callback if this is the final image to load
                            if (articles.indexOf(article) == articles.size() - 1) {
                                listener.onAllImagesLoaded(articleCards);
                            }
                        });
                    }

                    CustomImageRequest sourceIconRequest = new CustomImageRequest(article.getSourceIconUrl(), context);
                    sourceIconRequest.run(icon -> {
                        card.getSource().setIcon(icon);
                        // Callback if this is the final icon to load
                        if (articles.indexOf(article) == articles.size() - 1) {
                            listener.onAllIconsLoaded(articleCards);
                        }
                    });

                    articleCards.add(card);
                }

                listener.onAllTitlesLoaded(articleCards);
            }
        });
    }
}