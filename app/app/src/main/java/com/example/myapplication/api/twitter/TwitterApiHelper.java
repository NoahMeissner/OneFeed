package com.example.myapplication.api.twitter;

import static android.content.Context.MODE_PRIVATE;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.EndSessionRequest;
import net.openid.appauth.ResponseTypeValues;

import org.json.JSONException;

public class TwitterApiHelper {

    private final AuthorizationServiceConfiguration config;
    private final AuthState authState;
    private final AuthorizationService authService;

    // Todo: move to strings
    private final String oneFeedClientId = "TkJXRHNFaVg4eUpGYkVMaGtPWHI6MTpjaQ";
    private final String authCallbackUrl = "com.example.myapplication.redirect://callback";
    private final String logoutCallbackUrl = "com.example.myapplication.redirect://logout";

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
        Intent authIntent = authService.getAuthorizationRequestIntent(request);
        return authIntent;
    }

    // Handles the callback from the authentication prompt
    public void handleAuthenticationResponse(
            Context context,
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

    public interface AuthenticationListener {
        void onAuthenticated();
    }
}
