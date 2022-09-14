package com.example.myapplication.database;

import android.graphics.drawable.Drawable;

import com.example.myapplication.R;
import com.example.myapplication.data.addSource.Category;

import java.util.HashMap;

public class AddActivityInterestsIcons extends AddActivityIcons {

    private HashMap<Category.interests, Integer> interestsHashMap = new HashMap<>();

    public AddActivityInterestsIcons(){
        initHashMap();
    }

    private void initHashMap() {
        interestsHashMap.put(Category.interests.Politik,(R.drawable.business));
        interestsHashMap.put(Category.interests.Corona,R.drawable.coronavirus);
        interestsHashMap.put(Category.interests.Technik,R.drawable.tech);
        interestsHashMap.put(Category.interests.Gaming,R.drawable.sports);
        interestsHashMap.put(Category.interests.Sport,R.drawable.sport);
    }

    public HashMap<Category.interests,Integer> getHashMap(){
        return interestsHashMap;
    }

}
