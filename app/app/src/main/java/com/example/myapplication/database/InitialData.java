package com.example.myapplication.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.R;
import com.example.myapplication.data.addSource.Constants;
import com.example.myapplication.data.addSource.SourceAdd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class InitialData {

    /*
    This Method initial all important Data in the on boarding Process
    */

    /*
    Constants
     */
    private final Context context;
    private DataBaseHelper dataBaseHelper;
    private SharedPreferences.Editor editPreferences;
    private SharedPreferences sharedPreferences;
    private Set<String> selectedInterestsHashSet;

    /*
    Constructor
     */
    public InitialData(Context context){
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context);
        initSharedPreferences();
    }

    private void initSharedPreferences() {
        sharedPreferences = context.getSharedPreferences(context.getResources()
                .getString(R.string.initProcesBoolean), 0);
        editPreferences = sharedPreferences.edit();
    }

    public void setSelectedInterests(ArrayList<Constants.interests> selectedInterests) {
        selectedInterestsHashSet = new HashSet<>();
        for(Constants.interests interests: selectedInterests){
            selectedInterestsHashSet.add(interests.name());
        }
        editPreferences.putStringSet(Constants.Interests.name(),selectedInterestsHashSet);
        editPreferences.apply();
    }



    /*
    This Method returns all Interests which are token in the on boarding Process
     */
    public ArrayList<String> getSelectedInterests() {
        /*
        Here the information is taken from the SharedPreferences
         */
        selectedInterestsHashSet = new HashSet<>();
        selectedInterestsHashSet = sharedPreferences
                .getStringSet(Constants.Interests.name(),new HashSet<>());

        /*
        TO change the Object from String to Category.interests it is import to change the HashSet to
        a String Array
         */
        String[] array = selectedInterestsHashSet
                .toArray(new String[selectedInterestsHashSet.size()]);

        ArrayList<String> result = new ArrayList<>();

        for (String s : array) {
            result.add(s);
        }
        return result;
    }

    /*
    This Method saves all preferred SocialMedia Names
     */
    public void setSelectSocialMedia(ArrayList<Constants.socialMedia> socialMediaArrayList){
        Set<String> socialMedia = new HashSet<>();
        for(Constants.socialMedia socialMediaItem: socialMediaArrayList){
            socialMedia.add(socialMediaItem.name());
        }
        editPreferences.putStringSet(Constants.SocialMedia.name(),socialMedia);
        editPreferences.apply();
    }

    public ArrayList<String> getSelectedSocialMedia(){
         /*
        Here the information is taken from the SharedPreferences
         */
        Set<String> socialMedia = sharedPreferences
                .getStringSet(Constants.SocialMedia.name(),new HashSet<>());

        /*
        TO change the Object from String to Category.interests it is import to change the HashSet to
        a String Array
         */
        String[] array = socialMedia
                .toArray(new String[socialMedia.size()]);

        ArrayList<String> result = new ArrayList<>();

        for (String s : array) {
            result.add(s);
        }
        return result;
    }

    public void setArrayList(ArrayList<SourceAdd> arrayList) {
        dataBaseHelper.insertDataBase(arrayList);
    }
}
