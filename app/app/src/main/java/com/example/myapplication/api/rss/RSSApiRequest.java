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


public class RSSApiRequest {

    public interface OnResult {
        void titlesLoaded(ArrayList<NewsCard> articleResults);
        void iconsLoaded(ArrayList<NewsCard> articleResults);
        void imagesLoaded(ArrayList<NewsCard> articleResults);
    }

    public void loadArticlesForCategories(
            HashMap<Category.news, String> url,
            Context context,
            OnResult listener
    ) {
        ArrayList<NewsCard> cardsTitles = new ArrayList<>();
        ArrayList<NewsCard> cardsWithImages = new ArrayList<>();
        ArrayList<NewsCard> cardsWithIcons = new ArrayList<>();

        int currentIndex = 0;
        for (Map.Entry<Category.news, String> entry : url.entrySet()) {
            boolean isFinalRun = currentIndex == url.entrySet().size() - 1;

            loadArticlesForCategory(entry.getValue(), entry.getKey(), context, new OnResult() {
                @Override
                public void titlesLoaded(ArrayList<NewsCard> articleResults) {
                    cardsTitles.addAll(articleResults);
                    if (isFinalRun) {
                        listener.titlesLoaded(cardsTitles);
                    }
                }

                @Override
                public void iconsLoaded(ArrayList<NewsCard> articleResults) {
                    cardsWithIcons.addAll(articleResults);

                    // If all icons are loaded return to viewmodel
                    if (cardsWithImages.size() == cardsWithIcons.size()) {
                        listener.iconsLoaded(cardsWithIcons);
                    }
                }

                @Override
                public void imagesLoaded(ArrayList<NewsCard> articleResults) {
                    cardsWithImages.addAll(articleResults);

                    // If all images are loaded return to viewmodel
                    if (cardsWithImages.size() == cardsTitles.size()) {
                        listener.imagesLoaded(cardsWithImages);
                    }
                }
            });

            currentIndex++;
        }
    }

    private void loadArticlesForCategory(
            String url,
            Category.news category,
            Context context,
            OnResult listener) {

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest jsonObjectRequest = new StringRequest(
                url,
                response -> createArticles(response, category, context, listener),
                error -> Log.d("Error", "Erros")
        );
        queue.add(jsonObjectRequest);
    }

    private void createArticles(
            String xmlFile,
            Category.news newsCategory,
            Context context,
            OnResult listener
    ) {
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
                        listener.imagesLoaded(articleCards);
                }
                });
            }

            CustomImageRequest sourceIconRequest = new CustomImageRequest(article.getSourceIconUrl(), context);
            sourceIconRequest.run(icon -> {
                card.getSource().setIcon(icon);
                // Callback if this is the final icon to load
                if (articles.indexOf(article) == articles.size() - 1) {
                    listener.iconsLoaded(articleCards);
                }
            });

            articleCards.add(card);
        }

        listener.titlesLoaded(articleCards);
    }
}