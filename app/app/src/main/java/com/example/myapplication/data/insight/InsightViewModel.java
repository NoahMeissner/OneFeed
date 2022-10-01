package com.example.myapplication.data.insight;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.database.InsightRepository;
import com.github.mikephil.charting.data.BarEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class InsightViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> articlesPerDay;
    private MutableLiveData<Boolean> limitationIsEnabled;
    private final LiveData<List<NewsReadEntry>> newsReadList;

    InsightRepository insightRepository;
    private InsightPreferencesHelper insightPreferencesHelper;

    public InsightViewModel(Application application) {
        super(application);

        this.insightRepository = new InsightRepository(application);

        this.articlesPerDay = new MutableLiveData<>(1);
        this.limitationIsEnabled = new MutableLiveData<>();
        this.newsReadList = insightRepository.getNewsCurrentYear();

        this.insightPreferencesHelper = new InsightPreferencesHelper(application);
        this.limitationIsEnabled.setValue(insightPreferencesHelper.getLimitationIsEnabled(application));
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

    public LiveData<List<NewsReadEntry>> getNewsReadList() {
        return newsReadList;
    }

    public LiveData<Integer> getArticlesPerDay() {
        return articlesPerDay;
    }

    public LiveData<Boolean> getLimitationIsEnabled() {
        return limitationIsEnabled;
    }

    public List<BarEntry> convertIntoBarEntries(List<NewsReadEntry> entries) {
        ArrayList<BarEntry> results = new ArrayList<>();

        // Desired structure: e.g. 11 Articles read on 27.09.2022
        // Group all entries by date and check the amount of entries for this day
        Map<Long, List<NewsReadEntry>> groupedEntries = entries.stream().collect(
                Collectors.groupingBy(e -> e.getReadDate()));
        groupedEntries.forEach((dayLong, entriesGroup) -> {
            results.add(new BarEntry(LocalDate.ofEpochDay(dayLong).getDayOfYear(), entriesGroup.size()));
        });

        return results;
    }
}
