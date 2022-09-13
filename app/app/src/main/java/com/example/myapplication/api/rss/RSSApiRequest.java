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
    NewsSource sampleArticleSource = new NewsSource(
            "Spiegel", "https://www.spiegel.de/"
    );

    public interface OnResult {
        void result(ArrayList<NewsCard> articleResults);
    }

    public void loadArticlesForCategories(
            HashMap<Category.news, String> url,
            Context context,
            OnResult listener
    ) {
        for (Map.Entry<Category.news, String> entry : url.entrySet()) {
            loadArticlesForCategory(entry.getValue(), entry.getKey(), context, listener);
        }
    }

    private void loadArticlesForCategory(
            String url,
            Category.news category,
            Context context,
            OnResult listener) {

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest jsonObjectRequest = new StringRequest(url, response -> {
            createArticles(response, category, context, listener);
        }, error -> Log.d("Error", "Erros"));
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
            if (!article.getIconUrl().equals("")) {
                ImageRequest imageRequest = new ImageRequest(article.getIconUrl(), context);
                imageRequest.run(icon -> {
                    // Todo: move conversion into viewmodel
                    // Todo: use dynamic source for article
                    articleCards.add(
                            new ArticleCard(
                                    article.getTitle(),
                                    sampleArticleSource,
                                    article.getPublicationDate(),
                                    icon
                            ));

                    // Return if loading all articles is complete
                    if (articles.indexOf(article) == articles.size() - 1) {
                        listener.result(articleCards);
                    }
                });
            }
        }
    }
}