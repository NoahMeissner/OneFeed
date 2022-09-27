package com.example.myapplication.data.insight;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.addSource.Constants;


public class InsightViewModel extends ViewModel {
    private final String sharedPrefsKey =
            Constants.initial.ConsumptionAnalyse.name();
    private final String sharedPrefsArticlesPerDay =
            Constants.insightSettings.articlesPerDay.name();
    private final String sharedPrefsLimitationIsEnabled =
            Constants.insightSettings.limitationIsEnabled.name();

    private MutableLiveData<Integer> articlesPerDay;
    private MutableLiveData<Boolean> limitationIsEnabled;

    public InsightViewModel() {
        articlesPerDay = new MutableLiveData<>();
        limitationIsEnabled = new MutableLiveData<>();
    }

    // Todo: call in constructor with context parameter
    public void loadPreferences(Context context) {
        loadArticleLimitation(context);
        loadLimitationIsEnabled(context);
    }

    // Limitation toggle
    private void loadLimitationIsEnabled(Context context) {
        // Load from shared prefs
        SharedPreferences insightPrefs = context.getSharedPreferences(sharedPrefsKey, MODE_PRIVATE);
        boolean limitationIsEnabledLoaded = insightPrefs.getBoolean(sharedPrefsLimitationIsEnabled, false);

        // Set vm value
        this.limitationIsEnabled.setValue(limitationIsEnabledLoaded);
    }

    public void setLimitationIsEnabled(boolean limitationIsEnabled, Context context) {
        this.limitationIsEnabled.setValue(limitationIsEnabled);
        saveLimitationIsEnabled(context);
    }

    private void saveLimitationIsEnabled(Context context) {
        // Save to shared prefs
        SharedPreferences authPrefs = context.getSharedPreferences(sharedPrefsKey, MODE_PRIVATE);
        authPrefs.edit()
                .putBoolean(sharedPrefsLimitationIsEnabled, this.limitationIsEnabled.getValue())
                .apply();
    }

    // Articles read limitation
    private void loadArticleLimitation(Context context) {
        // Load from shared prefs
        SharedPreferences insightPrefs = context.getSharedPreferences(sharedPrefsKey, MODE_PRIVATE);
        int articlesPerDayLoaded = insightPrefs.getInt(sharedPrefsArticlesPerDay, 10);

        // Set vm value
        this.articlesPerDay.setValue(articlesPerDayLoaded);
    }

    public void setArticleLimitation(int articlesPerDay, Context context) {
        this.articlesPerDay.setValue(articlesPerDay);
        saveArticleLimitation(context);
    }

    private void saveArticleLimitation(Context context) {
        // Save to shared prefs
        SharedPreferences authPrefs = context.getSharedPreferences(sharedPrefsKey, MODE_PRIVATE);
        authPrefs.edit()
                .putInt(sharedPrefsArticlesPerDay, this.articlesPerDay.getValue())
                .apply();
    }

    public LiveData<Integer> getArticlesPerDay() {
        return articlesPerDay;
    }

    public LiveData<Boolean> getLimitationIsEnabled() {
        return limitationIsEnabled;
    }
}
