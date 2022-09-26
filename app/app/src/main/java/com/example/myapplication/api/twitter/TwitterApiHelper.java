package com.example.myapplication.api.twitter;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.api.NewsRepository;
import com.example.myapplication.data.card.TwitterCard;
import com.example.myapplication.data.feed.NewsSource;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.EndSessionRequest;
import net.openid.appauth.ResponseTypeValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TwitterApiHelper {

    private final AuthorizationServiceConfiguration config;
    private final AuthState authState;
    private final AuthorizationService authService;

    // Todo: move to strings
    private final String oneFeedClientId = "TkJXRHNFaVg4eUpGYkVMaGtPWHI6MTpjaQ";
    private final String authCallbackUrl = "com.example.myapplication.redirect://callback";
    private final String logoutCallbackUrl = "com.example.myapplication.redirect://logout";
    private final NewsSource twitterNewsSource = new NewsSource("Twitter");

    public TwitterApiHelper(Context context) {
        this.config = new AuthorizationServiceConfiguration(
                Uri.parse("https://twitter.com/i/oauth2/authorize"),
                Uri.parse("https://api.twitter.com/2/oauth2/token")
        );

        this.authState = readAuthState(context);
        this.authService = new AuthorizationService(context);
    }

    // Asks the user for authentication
    public Intent createAuthorizationIntent() {
        // Build auth request
        AuthorizationRequest.Builder authRequestBuilder =
                new AuthorizationRequest.Builder(
                        config, // the authorization service configuration
                        oneFeedClientId, // the client ID, typically pre-registered and static
                        ResponseTypeValues.CODE, // the response_type value: we want a code
                        Uri.parse(authCallbackUrl) // the redirect URI to which the auth response is sent
                );

        AuthorizationRequest request = authRequestBuilder
                .setScopes("tweet.read", "users.read")
                .build();

        // return intent
        return authService.getAuthorizationRequestIntent(request);
    }

    // Handles the callback from the authentication prompt
    public void handleAuthenticationResponse(
            AuthorizationResponse resp,
            AuthorizationException ex,
            AuthenticationListener listener
    ) {
        // Update state
        authState.update(resp, ex);

        if (resp != null) {
            // Get token from response
            this.authService.performTokenRequest(
                    resp.createTokenExchangeRequest(),
                    (resp1, ex1) -> {
                        if (resp1 != null) {
                            // exchange succeeded
                            authState.update(resp1, ex1);
                            listener.onAuthenticated();
                            Log.d("TAG", "onCreate: exchange success");
                        } else {
                            // exchange failed
                            Log.d("TAG", "onCreate: exchange failed");
                        }
                    });
        } else {
            // authorization failed, check ex for more details
            Log.d("TAG", "onCreate: failed auth: " + ex);
        }
    }

    public void performActionWithFreshTokens(
            @NonNull AuthorizationService service,
            @NonNull AuthState.AuthStateAction action) {
        this.authState.performActionWithFreshTokens(service, action);
    }

    // Ends the current session
    public void endSession(Context context) {
        EndSessionRequest endSessionRequest =
                new EndSessionRequest.Builder(config)
                        .setPostLogoutRedirectUri(Uri.parse(logoutCallbackUrl))
                        .build();

//        this.authService.performEndSessionRequest(
//                endSessionRequest,
//                PendingIntent.getActivity(
//                        context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_MUTABLE
//                ),
//                PendingIntent.getActivity(
//                        context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_MUTABLE
//                )
//        );

        // Todo: Implement endSession activity to handle result
        // Todo: Update authState
        //  https://github.com/openid/AppAuth-Android#ending-current-session
    }

    // Reads the authState object from shared preferences
    @NonNull
    public AuthState readAuthState(Context context) {
        SharedPreferences authPrefs = context.getSharedPreferences("auth", MODE_PRIVATE);
        String stateJson = authPrefs.getString("stateJson", null);
        if (stateJson != null) {
            try {
                return AuthState.jsonDeserialize(stateJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new AuthState(config);
    }

    // Saves the authState object to shared preferences
    public void writeAuthState(Context context) {
        SharedPreferences authPrefs = context.getSharedPreferences("auth", MODE_PRIVATE);
        authPrefs.edit()
                .putString("stateJson", authState.jsonSerializeString())
                .apply();
    }

    @NonNull
    public String parseUserId(JSONObject response) {
        String userId = "";

        try {
            JSONObject timelineResponseData = response.getJSONObject("data");
            userId = timelineResponseData.getString("id");
        } catch (JSONException e) {
            Log.e(TAG,
                    "loadArticlesForRssEndpoints: Failed to parse response for /users/me endpoint", e);
            return userId;
        }

        Log.d(TAG, "loadArticlesForRssEndpoints: userId: " + userId);
        return userId;
    }

    public void loadTweets(Context context, RequestQueue requestQueue, NewsRepository.LoadNewsCallback listener) {
        if (readAuthState(context) == null) {
            Log.d(TAG, "getTwitterTimeline: Authentication state for twitter is null.");
            listener.onTwitterFailed();
        }

        performActionWithFreshTokens(new AuthorizationService(context), (accessToken, idToken, ex) -> {
            if (ex != null) {
                // negotiation for fresh tokens failed, check ex for more details
                Log.d(TAG, "getTwitterTimeline: Failed to refresh token: " + ex);
                listener.onTwitterFailed();
            }

            // 1. Load user id
            // https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users-me
            String userInfoEndpoint = "https://api.twitter.com/2/users/me/";
            JsonObjectRequest rssRequest = new JsonObjectRequest(userInfoEndpoint, response -> {
                String userId = parseUserId(response);

                // 2. Get user timeline for user id
                // https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference
                if (userId.equals("")) {
                    Log.e(TAG, "loadArticlesForRssEndpoints: No userId for the " +
                            "/users/me endpoint was found. Can't load the timeline.");
                    return;
                }
                JsonObjectRequest timelineRequest = createUserTimelineRequest(userId, accessToken, requestQueue, listener);
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

    // Convenience function to get the necessary headers for api request
    private Map<String, String> getTwitterHeaders(String accessToken) {
        Map<String, String> params = new HashMap<>();
        params.put("Authorization", " BEARER " + accessToken);
        return params;
    }

    // Creates a volley request to get the twitter timeline with tweet author information from api
    private JsonObjectRequest createUserTimelineRequest(
            String userId,
            String accessToken,
            RequestQueue requestQueue,
            NewsRepository.LoadNewsCallback listener) {
        // Endpoint docs:
        //    https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference
        String timelineEndpoint = "https://api.twitter.com/2/users/" + userId + "/timelines/reverse_chronological";
        String timelineRequestUrl = timelineEndpoint +
                "?tweet.fields=author_id,created_at" +
                "&user.fields=profile_image_url,username,name" +
                "&expansions=author_id";

        JsonObjectRequest timelineRequest =
                new JsonObjectRequest(timelineRequestUrl, timelineResponse -> {
            ArrayList<TwitterCard> results = new ArrayList<>();

            Log.d(TAG, "createUserTimelineRequest: got tweets response: ");
            Log.d(TAG, timelineResponse.toString());

            try {
                // Tweet authors are saved in a separate list
                ArrayList<TwitterApiUser> tweetAuthors = parseAuthorsInfos(timelineResponse);

                // Parse the tweets and load images
                JSONArray timelineResults = timelineResponse.getJSONArray("data");
                for (int indexTweets = 0; indexTweets < timelineResults.length(); indexTweets++) {
                    int finalIndexTweets = indexTweets;
                    // Loads a tweet with all required information
                    loadTweet(
                            tweetAuthors,
                            timelineResults,
                            finalIndexTweets,
                            requestQueue,
                            twitterCard -> {
                                // Add to results
                                results.add(twitterCard);
                                // Return if all tweets are loaded
                                if (finalIndexTweets == timelineResults.length() - 1) {
                                    Log.d(TAG,
                                            "createUserTimelineRequest: loaded all tweets: " +
                                                    results.stream().count() + " tweets loaded."
                                    );
                                    listener.onTwitterComplete(results);
                                }
                            }
                    );
                }
            } catch (JSONException e) {
                Log.e(TAG,
                        "createUserTimelineRequest: Failed to parse response for " +
                                "/timelines/reverse_chronological endpoint", e);
            }
        }, error -> Log.d(TAG, "onErrorResponse: " + error.getMessage())) {
            @Override
            public Map<String, String> getHeaders() {
                return getTwitterHeaders(accessToken);
            }
        };

        return timelineRequest;
    }

    // Loads image for twitter user and creates a twitter card from provided infos
    private void loadTweet(
            ArrayList<TwitterApiUser> tweetAuthors,
            JSONArray timelineResults,
            int indexTweets,
            RequestQueue requestQueue,
            LoadTweetListener listener
    ) throws JSONException {
        String text = timelineResults.getJSONObject(indexTweets).getString("text");
        String authorId = timelineResults.getJSONObject(indexTweets).getString("author_id");
        String createdAt = timelineResults.getJSONObject(indexTweets).getString("created_at");
        LocalDateTime parsedCrateDate = ZonedDateTime.parse(createdAt).toLocalDateTime();
        String tweetId = timelineResults.getJSONObject(indexTweets).getString("id");
        String webUrl = "https://twitter.com/" + authorId + "/status/" + tweetId;

        // Link the right author
        TwitterApiUser author = tweetAuthors.stream()
                .filter(u -> u.id.equals(authorId)).findFirst().orElse(null);
        if (author == null) return;

        // Load profile picture url for the author
        ImageRequest authorImageRequest = new ImageRequest(author.imageUrl, response -> {
            listener.onLoaded(
                    new TwitterCard(
                            twitterNewsSource,
                            parsedCrateDate,
                            webUrl,
                            text,
                            author.name,
                            author.username,
                            response
                    ));

            Log.d(TAG, "createUserTimelineRequest: Added twitter card to results");

        }, 0, 0, ImageView.ScaleType.CENTER, null, error -> {
            Log.e(TAG,
                "createUserTimelineRequest: Failed to load profile icon from "
                + author.imageUrl
            );
        });

        requestQueue.add(authorImageRequest);
    }

    // Parses an author from the users reponse from the api
    @NonNull
    private ArrayList<TwitterApiUser> parseAuthorsInfos(JSONObject timelineResponse) throws JSONException {
        JSONObject userIncludes = timelineResponse.getJSONObject("includes");
        JSONArray users = userIncludes.getJSONArray("users");
        ArrayList<TwitterApiUser> userInfos = new ArrayList<>();
        for(int indexUsers = 0; indexUsers < users.length(); indexUsers++) {
            userInfos.add(new TwitterApiUser(
                    users.getJSONObject(indexUsers).getString("id"),
                    users.getJSONObject(indexUsers).getString("name"),
                    users.getJSONObject(indexUsers).getString("username"),
                    users.getJSONObject(indexUsers).getString("profile_image_url")
            ));
        }
        return userInfos;
    }

    // Represents the information provided by the twitter api about a user
    private class TwitterApiUser {
        private String id;
        private String name;
        private String username;
        private String imageUrl;

        public TwitterApiUser(String id, String name, String username, String imageUrl) {
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

    public interface LoadTweetListener {
        void onLoaded(TwitterCard twitterCard);
    }

    public interface AuthenticationListener {
        void onAuthenticated();
    }
}
