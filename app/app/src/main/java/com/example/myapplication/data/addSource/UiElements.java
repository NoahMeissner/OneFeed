package com.example.myapplication.data.addSource;

import android.annotation.SuppressLint;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UiElements {

    /*
    This Method includes an PictureID HashMap and all Sources Hashmap
    which can be hand over when necessary
     */

    /*
    Constants
     */
    private final HashMap<Constants,ArrayList<String>> arrayListHashMap = new HashMap<>();

    private final HashMap<String, Integer> pictureHashMap = new HashMap<>();


    /*
    set initPicture HashMap
     */
    private void iniPictureHashMap() {
        pictureHashMap.put(
                Constants.news.FAZ.name(),
                R.drawable.picture_faz);

        pictureHashMap.put(
                Constants.news.Spiegel.name(),
                R.drawable.picture_spiegel);

        pictureHashMap.put(
                Constants.interests.Politik.name(),
                (R.drawable.icon_world));

        pictureHashMap.put(
                Constants.interests.Corona.name(),
                R.drawable.icon_coronavirus);

        pictureHashMap.put(
                Constants.interests.Technik.name(),
                R.drawable.icon_tech);

        pictureHashMap.put(
                Constants.interests.Gaming.name(),
                R.drawable.icon_gaming);

        pictureHashMap.put(
                Constants.interests.Sport.name(),
                R.drawable.icon_sport);
        pictureHashMap.put(Constants.interests.Wirtschaft.name(),
                R.drawable.icon_business);

        pictureHashMap.put(
                Constants.socialMedia.Twitter.name(),
                R.drawable.icon_twitter);
    }

    /*
    set Source HashMap
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void initSource() {

        arrayListHashMap.put(Constants.Interests,
                new ArrayList<>(Stream.of(Constants.interests.values())
                        .map(Enum::name)
                        .collect(Collectors.toList())));

        arrayListHashMap.put(Constants.SocialMedia,
                new ArrayList<>(Stream.of(Constants.socialMedia.values())
                        .map(Enum::name)
                        .collect(Collectors.toList())));

        arrayListHashMap.put(Constants.Newspaper,
                new ArrayList<>(Stream.of(Constants.news.values())
                        .map(Enum::name)
                        .collect(Collectors.toList())));
    }

    /*
    This Method initialise the Picture HashMap
     */
    public void initialPictureHashMap(){
        iniPictureHashMap();
    }

    /*
    Getter Methods
     */
    public Integer getPictureId(String name){
        return pictureHashMap.get(name);
    }

    public HashMap<Constants, ArrayList<String>> getArrayListHashMap() {
        initSource();
        return arrayListHashMap;
    }

    public HashMap<String, Integer> getPictureHashMap() {
        iniPictureHashMap();
        return pictureHashMap;
    }
}
