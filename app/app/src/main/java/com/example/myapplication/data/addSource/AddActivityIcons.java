package com.example.myapplication.data.addSource;

import com.example.myapplication.R;
import com.example.myapplication.data.addSource.Category;

import java.util.HashMap;

public class AddActivityIcons {

    private final HashMap<Category.interests, Integer> interestsHashMap = new HashMap<>();
    private final HashMap<Category.socialMedia, Integer> socialMediaHashMap = new HashMap<>();
    private final HashMap<Category.news, Integer> newsHashMap = new HashMap<>();


    public AddActivityIcons(){
        initInterestsHashMap();
        initSocialMediaHashMap();
        initNewsHashMap();
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
                (R.drawable.business));

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
    }

    public HashMap<Category.interests, Integer> getInterestsHashMap() {
        return interestsHashMap;
    }

    public HashMap<Category.socialMedia, Integer> getSocialMediaHashMap() {
        return socialMediaHashMap;
    }

    public HashMap<Category.news, Integer> getNewsHashMap() {
        return newsHashMap;
    }
}
