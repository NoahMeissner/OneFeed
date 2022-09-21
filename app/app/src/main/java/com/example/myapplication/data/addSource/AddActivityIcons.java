package com.example.myapplication.data.addSource;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AddActivityIcons {

    private final HashMap<Category.interests, Integer> interestsHashMap = new HashMap<>();
    private final HashMap<Category,ArrayList<SourceAdd>> arrayListHashMap = new HashMap<>();
    private final HashMap<Category.socialMedia, Integer> socialMediaHashMap = new HashMap<>();
    private final HashMap<Category.news, Integer> newsHashMap = new HashMap<>();
    private final Context context;


    public AddActivityIcons(Context context){
        this.context = context;
    }

    private void initNewsHashMap() {
        newsHashMap.put(
                Category.news.FAZ,
                R.drawable.faz);

        newsHashMap.put(
                Category.news.Spiegel,
                R.drawable.spiegel);
    }

    private void initSocialMediaHashMap() {
        socialMediaHashMap.put(
                Category.socialMedia.Twitter,
                R.drawable.twitter_icon);

        socialMediaHashMap.put(
                Category.socialMedia.Reddit,
                R.drawable.reddit_icon);
    }

    private void initInterestsHashMap() {
        interestsHashMap.put(
                Category.interests.Politik,
                (R.drawable.world));

        interestsHashMap.put(
                Category.interests.Corona,
                R.drawable.coronavirus);

        interestsHashMap.put(
                Category.interests.Technik,
                R.drawable.tech);

        interestsHashMap.put(
                Category.interests.Gaming,
                R.drawable.sports);

        interestsHashMap.put(
                Category.interests.Sport,
                R.drawable.sport);
        interestsHashMap.put(Category.interests.Wirtschaft,
                R.drawable.business);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initSource() {
        arrayListHashMap.put(Category.Interests, new ArrayList<>());
        arrayListHashMap.put(Category.SocialMedia, new ArrayList<>());
        arrayListHashMap.put(Category.Newspaper, new ArrayList<>());

        AddActivityIcons addActivityIcons = new AddActivityIcons(context);

        for (Category.interests categoryInterests : addActivityIcons
                .getInterestsHashMap().keySet()) {

            Objects.requireNonNull(arrayListHashMap.get(Category.Interests)).
                    add(new SourceAdd(
                            categoryInterests.name(),
                            context.getDrawable(Objects.requireNonNull(
                                    addActivityIcons.getInterestsHashMap().get(categoryInterests))),
                            Category.Interests));
        }
        for (Category.news categoryNews : addActivityIcons.getNewsHashMap().keySet()) {
            Objects.requireNonNull(arrayListHashMap.get(Category.Newspaper)).
                    add(new SourceAdd(
                            categoryNews.name(),
                            context.getDrawable(Objects.requireNonNull(
                                    addActivityIcons.getNewsHashMap().get(categoryNews))),
                            Category.Newspaper));
        }
        for (Category.socialMedia categorySocialMedia : addActivityIcons
                .getSocialMediaHashMap().keySet()) {

            Objects.requireNonNull(arrayListHashMap.get(Category.SocialMedia)).
                    add(new SourceAdd(
                            categorySocialMedia.name(),
                            context.getDrawable(Objects.requireNonNull(
                                    addActivityIcons
                                            .getSocialMediaHashMap().get(categorySocialMedia))),
                            Category.SocialMedia));
        }
    }


    public HashMap<Category, ArrayList<SourceAdd>> getArrayListHashMap() {
        initSource();
        return arrayListHashMap;
    }

    public HashMap<Category.interests, Integer> getInterestsHashMap() {
        initInterestsHashMap();
        return interestsHashMap;
    }

    public HashMap<Category.socialMedia, Integer> getSocialMediaHashMap() {
        initSocialMediaHashMap();
        return socialMediaHashMap;
    }

    public HashMap<Category.news, Integer> getNewsHashMap() {
        initNewsHashMap();
        return newsHashMap;
    }

}
