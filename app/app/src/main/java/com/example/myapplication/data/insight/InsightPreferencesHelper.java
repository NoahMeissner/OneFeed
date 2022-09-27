package com.example.myapplication.data.insight;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.data.addSource.Constants;

public class InsightPreferencesHelper {

    // Keys for the shared preferences
    private final String sharedPrefsKey =
            Constants.initial.ConsumptionAnalyse.name();
    private final String sharedPrefsArticlesPerDayLimit =
            Constants.insightSettings.articlesPerDay.name();
    private final String sharedPrefsLimitIsEnabled =
            Constants.insightSettings.limitationIsEnabled.name();

    // Insights can limit how many articles a user can open per day
    private boolean limitIsEnabled = false;
    private int amountArticlesPerDayLimit;

    public InsightPreferencesHelper(Context context) {
        loadAmountArticlesPerDayLimit(context);
        loadLimitIsEnabled(context);
    }

    // Loads the amount of articles a user can open each day
    private void loadAmountArticlesPerDayLimit(Context context) {
        SharedPreferences insightPrefs = context.getSharedPreferences(sharedPrefsKey, MODE_PRIVATE);
        this.amountArticlesPerDayLimit = insightPrefs.getInt(sharedPrefsArticlesPerDayLimit, 1);
    }

    // Loads wether or not the insight feature is enabled
    private void loadLimitIsEnabled(Context context) {
        SharedPreferences insightPrefs = context.getSharedPreferences(sharedPrefsKey, MODE_PRIVATE);
        this.limitIsEnabled = insightPrefs.getBoolean(sharedPrefsLimitIsEnabled, false);
    }

    public void setLimitationIsEnabled(Context context, boolean limitIsEnabled) {
        // Save to shared prefs
        SharedPreferences authPrefs = context.getSharedPreferences(sharedPrefsKey, MODE_PRIVATE);
        authPrefs.edit()
                .putBoolean(sharedPrefsLimitIsEnabled, limitIsEnabled)
                .apply();
    }

    public void setAmountArticlesPerDayLimit(Context context, int amountArticlesPerDay) {
        // Save to shared prefs
        SharedPreferences authPrefs = context.getSharedPreferences(sharedPrefsKey, MODE_PRIVATE);
        authPrefs.edit()
                .putInt(sharedPrefsArticlesPerDayLimit, amountArticlesPerDay)
                .apply();
    }

    public int getAmountArticlesPerDayLimit(Context context) {
        loadAmountArticlesPerDayLimit(context);
        return amountArticlesPerDayLimit;
    }

    public boolean isLimitIsEnabled() {
        return limitIsEnabled;
    }
}
