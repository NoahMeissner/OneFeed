package com.example.myapplication.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.R;
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.addSource.SourceAdd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class InitialData {



    private final Context context;

    private DataBaseHelper dataBaseHelper;

    private SharedPreferences.Editor editPreferences;

    private SharedPreferences sharedPreferences;

    private Set<String> selectedInterestsHashSet;



    public InitialData(Context context){
        this.context = context;
        initDatabase();
        initSharedPreferences();
    }

    private void initSharedPreferences() {
        sharedPreferences = context.getSharedPreferences(context.getResources()
                .getString(R.string.initProcesBoolean), 0);
        editPreferences = sharedPreferences.edit();
    }

    private void initDatabase() {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public void setSelectedInterests(ArrayList<Category.interests> selectedInterests) {
        selectedInterestsHashSet = new HashSet<>();
        for(Category.interests interests: selectedInterests){
            selectedInterestsHashSet.add(interests.name());
        }
        editPreferences.putStringSet(Category.Interests.name(),selectedInterestsHashSet);
        editPreferences.apply();
    }



    /*
    This Method returns all Interests which are token in the on boarding Process
     */
    public ArrayList<Category.interests> getSelectedInterests() {
        /*
        Here the information is taken from the SharedPreferences
         */
        selectedInterestsHashSet = new HashSet<>();
        selectedInterestsHashSet = sharedPreferences
                .getStringSet(Category.Interests.name(),new HashSet<>());

        /*
        TO change the Object from String to Category.interests it is import to change the HashSet to
        a String Array
         */
        String[] array = selectedInterestsHashSet
                .toArray(new String[selectedInterestsHashSet.size()]);

        ArrayList<Category.interests> result = new ArrayList<>();

        for (String s : array) {
            result.add(Category.interests.valueOf(s));
        }
        return result;
    }

    /*
    This Method saves all preferred SocialMedia Names
     */
    public void setSelectSocialMedia(ArrayList<Category.socialMedia> socialMediaArrayList){
        Set<String> socialMedia = new HashSet<>();
        for(Category.socialMedia socialMediaItem: socialMediaArrayList){
            socialMedia.add(socialMediaItem.name());
        }
        editPreferences.putStringSet(Category.SocialMedia.name(),socialMedia);
        editPreferences.apply();
    }

    public ArrayList<Category.socialMedia> getSelectedSocialMedia(){
         /*
        Here the information is taken from the SharedPreferences
         */
        Set<String> socialMedia = sharedPreferences
                .getStringSet(Category.SocialMedia.name(),new HashSet<>());

        /*
        TO change the Object from String to Category.interests it is import to change the HashSet to
        a String Array
         */
        String[] array = socialMedia
                .toArray(new String[socialMedia.size()]);

        ArrayList<Category.socialMedia> result = new ArrayList<>();

        for (String s : array) {
            result.add(Category.socialMedia.valueOf(s));
        }
        return result;
    }

    public void setArrayList(ArrayList<SourceAdd> arrayList) {
        dataBaseHelper.insertDataBase(arrayList);
    }
}
