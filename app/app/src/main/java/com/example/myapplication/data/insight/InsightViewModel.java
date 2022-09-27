package com.example.myapplication.data.insight;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class InsightViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> articlesPerDay;
    private MutableLiveData<Boolean> limitationIsEnabled;

    private InsightPreferencesHelper insightPreferencesHelper;

    public InsightViewModel(Application application) {
        super(application);

        articlesPerDay = new MutableLiveData<>(1);
        limitationIsEnabled = new MutableLiveData<>();

        insightPreferencesHelper = new InsightPreferencesHelper(application);
        this.limitationIsEnabled.setValue(insightPreferencesHelper.isLimitIsEnabled());
        this.articlesPerDay.setValue(insightPreferencesHelper.getAmountArticlesPerDayLimit(application));
    }

    public void setLimitationIsEnabled(Context context, boolean limitationIsEnabled) {
        this.limitationIsEnabled.setValue(limitationIsEnabled);
        insightPreferencesHelper.setLimitationIsEnabled(context, limitationIsEnabled);
    }

    public void setArticleLimitation(Context context, int articlesPerDay) {
        this.articlesPerDay.setValue(articlesPerDay);
        insightPreferencesHelper.setAmountArticlesPerDayLimit(context, articlesPerDay);
    }

    public LiveData<Integer> getArticlesPerDay() {
        return articlesPerDay;
    }

    public LiveData<Boolean> getLimitationIsEnabled() {
        return limitationIsEnabled;
    }
}
