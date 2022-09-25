package com.example.myapplication.data.addSource;

import android.annotation.SuppressLint;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UiElements {

    private final HashMap<Category,ArrayList<String>> arrayListHashMap = new HashMap<>();

    private final HashMap<String, Integer> pictureHashMap = new HashMap<>();


    public UiElements(){
    }

    private void iniPictureHashMap() {
        pictureHashMap.put(
                Category.news.FAZ.name(),
                R.drawable.faz);

        pictureHashMap.put(
                Category.news.Spiegel.name(),
                R.drawable.spiegel);

        pictureHashMap.put(
                Category.interests.Politik.name(),
                (R.drawable.world));

        pictureHashMap.put(
                Category.interests.Corona.name(),
                R.drawable.coronavirus);

        pictureHashMap.put(
                Category.interests.Technik.name(),
                R.drawable.tech);

        pictureHashMap.put(
                Category.interests.Gaming.name(),
                R.drawable.sports);

        pictureHashMap.put(
                Category.interests.Sport.name(),
                R.drawable.sport);
        pictureHashMap.put(Category.interests.Wirtschaft.name(),
                R.drawable.business);

        pictureHashMap.put(
                Category.socialMedia.Twitter.name(),
                R.drawable.twitter_icon);

        pictureHashMap.put(
                Category.socialMedia.Reddit.name(),
                R.drawable.reddit_icon);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initSource() {

        arrayListHashMap.put(Category.Interests,
                new ArrayList<>(Stream.of(Category.interests.values())
                        .map(Enum::name)
                        .collect(Collectors.toList())));

        arrayListHashMap.put(Category.SocialMedia,
                new ArrayList<>(Stream.of(Category.socialMedia.values())
                        .map(Enum::name)
                        .collect(Collectors.toList())));

        arrayListHashMap.put(Category.Newspaper,
                new ArrayList<>(Stream.of(Category.news.values())
                        .map(Enum::name)
                        .collect(Collectors.toList())));
    }

    public void initialPictureHashMap(){
        iniPictureHashMap();
    }

    public Integer getPictureId(String name){
        return pictureHashMap.get(name);
    }


    public HashMap<Category, ArrayList<String>> getArrayListHashMap() {
        initSource();
        return arrayListHashMap;
    }

    public HashMap<String, Integer> getPictureHashMap() {
        iniPictureHashMap();
        return pictureHashMap;
    }
}
