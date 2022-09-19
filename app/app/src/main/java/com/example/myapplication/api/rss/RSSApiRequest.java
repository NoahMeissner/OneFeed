package com.example.myapplication.api.rss;
import com.example.myapplication.data.addSource.Category;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.fragment.feed.ErrorFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RSSApiRequest {

    private onResult listener;

    public interface onResult{
        void result(ArrayList<RSSArticle> rssArticles);
    }

    public void  makeRSSCategory(HashMap<Category.news,String> url,Context context,onResult listener){

        for(Map.Entry<Category.news, String> entry : url.entrySet()){
            initialCategoryAPIRequest(entry.getValue(), entry.getKey(), context,listener);
        }
    }

    private void initialCategoryAPIRequest(
            String url ,
            Category.news category,
            Context context,
            onResult listener){

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest jsonObjectRequest = new StringRequest(url, response -> {
                getFeed(response,category,context,listener);
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ERROR","ERROR");
                    //@TODO Error Fragement aktivieren
                }
            });
            queue.add(jsonObjectRequest);
    }

    private void getFeed(String xmlFile, Category.news newsCategory, Context context,onResult listener) {
        RSSHelper helper = new RSSHelper();
        ArrayList<RSSArticle> articles = helper.createArticlesFromResponse(
                xmlFile,
                newsCategory
        );

        Log.d("TAG", "Amount of articles " + articles.size());

        for (RSSArticle article : articles) {
            if (!article.getIconUrl().equals("")) {
                ImageRequest imageRequest = new ImageRequest(article.getIconUrl(), context);
                imageRequest.run(new ImageRequest.RequestListener() {
                    @Override
                    public void onResult(Bitmap icon) {
                        article.setBitmap(icon);
                        listener.result(articles);
                    }
                });
            }
        }
    }
}
