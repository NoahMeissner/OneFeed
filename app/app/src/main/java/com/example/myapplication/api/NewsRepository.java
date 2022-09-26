package com.example.myapplication.api;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.api.rss.RssArticle;
import com.example.myapplication.api.rss.RssArticleParser;
import com.example.myapplication.api.twitter.TwitterApiHelper;
import com.example.myapplication.data.addSource.Constants;
import com.example.myapplication.data.card.ArticleCard;
import com.example.myapplication.data.card.NewsCard;
import com.example.myapplication.data.card.TwitterCard;
import com.example.myapplication.data.feed.NewsSource;

import net.openid.appauth.AuthorizationService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NewsRepository {

    private ExecutorService executor;
    private RequestQueue requestQueue;
    private TwitterApiHelper twitterApi;

    public NewsRepository(Context context) {
        this.twitterApi = new TwitterApiHelper(context);
    }

    // Loads all articles for the specified rss urls by making multiple requests
    //   (one request per rss endpoint)
    public void loadNews(
            HashMap<Constants.news, String> rssEndpoints,
            Context context,
            NewsCardsCallback listener
    ) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.executor = Executors.newFixedThreadPool(2);

        executor.execute(() -> {
            ArrayList<NewsCard> cards = new ArrayList<>();

            loadNews(rssEndpoints, context, new LoadNewsCallback() {
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
                public void onTwitterComplete(ArrayList<TwitterCard> tweetResults) {
                    cards.addAll(tweetResults);
                    twitterComplete = true;
                    Log.d(TAG, "onTwitterComplete: loaded twitter cards");

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

    private void loadNews(HashMap<Constants.news, String> rssEndpoints, Context context, LoadNewsCallback listener) {
        // Load rss articles for all categories
        loadArticles(rssEndpoints, context, listener);

        // Load all tweets
        loadTweets(context, listener);
    }

    private void loadTweets(Context context, LoadNewsCallback listener) {
        // Todo: add .accessToken?
        if (twitterApi.readAuthState(context) != null) {
            twitterApi.performActionWithFreshTokens(new AuthorizationService(context), (accessToken, idToken, ex) -> {
                if (ex != null) {
                    // negotiation for fresh tokens failed, check ex for more details
                    Log.d(TAG, "getTwitterTimeline: Failed to refresh token: " + ex);
                    return;
                }

                // 1. Get user id
                // https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users-me
                String userInfoEndpoint = "https://api.twitter.com/2/users/me/";
                JsonObjectRequest rssRequest = new JsonObjectRequest(userInfoEndpoint, response -> {
                    String userId = "";
                    try {
                        JSONObject timelineResponseData = response.getJSONObject("data");
                        userId = timelineResponseData.getString("id");
                    } catch (JSONException e) {
                        Log.e(TAG, "loadArticlesForRssEndpoints: Failed to parse response for /users/me endpoint", e);
                    }

                    Log.d(TAG, "loadArticlesForRssEndpoints: userId: " + userId);

                    // 2. Get user timeline
                    // https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference
                    if (userId.equals("")) {
                        Log.e(TAG, "loadArticlesForRssEndpoints: No userId for the /users/me endpoint was found. Can't load the timeline.");
                        return;
                    }
                    JsonObjectRequest timelineRequest = createUserTimelineRequest(userId, accessToken, listener);
                    requestQueue.add(timelineRequest);
                }, error -> Log.d(TAG, "onErrorResponse for userInfo: " + error.getMessage())) {
                    @Override
                    public Map<String, String> getHeaders() {
                        return getTwitterHeaders(accessToken);
                    }
                };

                requestQueue.add(rssRequest);
            });
        }
    }

    private void loadArticles(HashMap<Constants.news, String> rssEndpoints, Context context, LoadNewsCallback listener) {
        ArrayList<ArticleCard> articleCards = new ArrayList<>();
        int currentIndex = 0;
        for (Map.Entry<Constants.news, String> entry : rssEndpoints.entrySet()) {
            // Load all articles and notify listener when all data has been loaded
            boolean isFinalRun = currentIndex == rssEndpoints.entrySet().size() - 1;
            loadArticlesForRssEndpoint(
                    entry.getValue(),
                    entry.getKey(),
                    context,
                    articleResults -> {
                        articleCards.addAll(articleResults);

                        if (isFinalRun) {
                            listener.onRssComplete(articleCards);
                        }
                    });

            currentIndex++;
        }
    }

    private JsonObjectRequest createUserTimelineRequest(String userId, String accessToken, LoadNewsCallback listener) {
        // https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference
        String timelineEndpoint = "https://api.twitter.com/2/users/" + userId + "/timelines/reverse_chronological";
        String timelineRequestUrl = timelineEndpoint +
                "?tweet.fields=author_id,created_at" +
                "&user.fields=profile_image_url,username,name" +
                "&expansions=author_id";

        JsonObjectRequest timelineRequest = new JsonObjectRequest(timelineRequestUrl, timelineResponse -> {
            ArrayList<TwitterCard> results = new ArrayList<>();

            Log.d(TAG, "createUserTimelineRequest: got tweets response: ");
            Log.d(TAG, timelineResponse.toString());

            NewsSource twitterNewsSource = new NewsSource("Twitter");
            try {
                JSONArray timelineResults = timelineResponse.getJSONArray("data");
                for (int i = 0; i < timelineResults.length(); i++) {
                    String text = timelineResults.getJSONObject(i).getString("text");
                    String authorId = timelineResults.getJSONObject(i).getString("author_id");
                    String createdAt = timelineResults.getJSONObject(i).getString("created_at");
                    LocalDateTime parsedCrateDate = ZonedDateTime.parse(createdAt).toLocalDateTime();
                    String tweetId = timelineResults.getJSONObject(i).getString("id");
                    String webUrl = "https://twitter.com/" + authorId + "/status/" + tweetId;

                    // Author details
                    JSONObject userIncludes = timelineResponse.getJSONObject("includes");
                    JSONArray users = userIncludes.getJSONArray("users");
                    ArrayList<TwitterApiUserInformation> userInfos = new ArrayList<>();
                    for(int j = 0; i < users.length(); i++) {
                        userInfos.add(new TwitterApiUserInformation(
                                authorId,
                                users.getJSONObject(i).getString("name"),
                                users.getJSONObject(i).getString("username"),
                                users.getJSONObject(i).getString("profile_image_url")
                        ));
                    }

                    TwitterApiUserInformation author = userInfos.stream().filter(u -> u.id == authorId).findFirst().orElse(null);
                    Log.d(TAG, "createUserTimelineRequest: author:" + author);
                    if (author == null) return;

                    // Load profile picture url
                    int finalI = i;
                    ImageRequest authorImageRequest = new ImageRequest(author.imageUrl, response -> {
                        results.add(
                                new TwitterCard(
                                        twitterNewsSource,
                                        parsedCrateDate,
                                        webUrl,
                                        text,
                                        author.name,
                                        author.username,
                                        response
                                ));
                        Log.d(TAG, "createUserTimelineRequest: Added twitter card to result");
                        if (finalI == timelineResults.length() - 1) {
                            Log.d(TAG, "createUserTimelineRequest: loaded Tweets: " + results.stream().count());
                            listener.onTwitterComplete(results);
                        }
                    }, 0, 0, ImageView.ScaleType.CENTER, null, error -> {
                        Log.e(TAG, "createUserTimelineRequest: Failed to load profile icon via " + author.imageUrl);
                    });

                    requestQueue.add(authorImageRequest);
                }
            } catch (JSONException e) {
                Log.e(TAG, "createUserTimelineRequest: Failed to parse response for /timelines/reverse_chronological endpoint", e);
            }
        }, error -> Log.d(TAG, "onErrorResponse: " + error.getMessage())) {
            @Override
            public Map<String, String> getHeaders() {
                return getTwitterHeaders(accessToken);
            }

//            @Override
//            public Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
////                params.put("expansions", "author_id");
//                params.put("tweet.fields", "author_id,created_at");
////                params.put("user.fields", "name,username,profile_image_url");
//                return params;
//            }
        };
        Log.d(TAG, "createUserTimelineRequest: created timeline request: " + timelineRequest.getUrl());

        return timelineRequest;
    }

    private Map<String, String> getTwitterHeaders(String accessToken) {
        Map<String, String> params = new HashMap<>();
        params.put("Authorization", " BEARER " + accessToken);
        return params;
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
        void onTwitterComplete(ArrayList<TwitterCard> tweetResults);
    }

    // Callback for loading multiple Article cards
    public interface ArticleCardsCallback {
        void onComplete(ArrayList<ArticleCard> articleCardsResults);
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

    private class TwitterApiUserInformation {
        private String id;
        private String name;
        private String username;
        private String imageUrl;

        public TwitterApiUserInformation(String id, String name, String username, String imageUrl) {
            this.id = id;
            this.name = name;
            this.username = username;
            this.imageUrl = imageUrl;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getUsername() {
            return username;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
}