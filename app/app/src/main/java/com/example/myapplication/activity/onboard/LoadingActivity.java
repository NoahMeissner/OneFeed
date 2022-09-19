package com.example.myapplication.activity.onboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.activity.FeedActivity;
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.addSource.SourceAdd;
import com.example.myapplication.database.InitialData;

import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity {

    /*
    This Activity Displays the Loading Activity
     */

    /*
    Constants
     */
    private boolean isInitialized =false;
    private boolean notification = false;
    private final boolean enabled = true;
    private boolean interestsAreInitialized = false;
    private SharedPreferences pref;
    SharedPreferences.Editor editPreferences;


    /*
    This method calls all methods of the class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSharedPreferences();
        initUi();
    }

    /*
    This method initialises the Shared Preferences to get the stored data.
     */
    private void initSharedPreferences() {
        pref = getSharedPreferences(getResources()
                .getString(R.string.initProcesBoolean), 0);
        isInitialized = pref.getBoolean(
                Category.initial.Process.name(),false);

        notification = pref.getBoolean(
                Category.initial.Notification.name(), false);

        interestsAreInitialized = pref.getBoolean(
                Category.initial.InterestsAreInitialised.name(), false);
    }

    /*
    This method checks whether the initialisation process
     has already been completed and opens the new activity.
     */
    private void initUi() {
        if(!isInitialized){
            Intent intent = new Intent(this, OnboardActivity.class);
            startActivity(intent);
            return;
        }
        setUpInterests();
        Intent intent = new Intent(getBaseContext(), FeedActivity.class);
        startActivity(intent);
    }


    /*
    This method converts the categories that have been selected in the on boarding
     process into category objects.
     */
    private void setUpInterests(){
        //@TODO DATABASE
        ArrayList<SourceAdd> sourceInterests = new ArrayList<>();
        InitialData data;

        if(!interestsAreInitialized){
            data = new InitialData(this);
            setSources(
                    data.getSelectedInterests(),
                    data.getSelectedSocialMedia(),
                    sourceInterests,
                    data
            );
            editPreferences = pref.edit();
            editPreferences.putBoolean(Category.initial.InterestsAreInitialised.name(), true);
            editPreferences.apply();
        }
    }

    private void setSources(ArrayList<Category.interests> interests,
                            ArrayList<Category.socialMedia> socialMedia,
                            ArrayList<SourceAdd> sourceInterests,
                            InitialData data){
        /*
        In these For loop, the selected interests and social Media are converted
        into objects and passed to the array list
        */
        for(Category.interests interestCategory: interests){
            SourceAdd sourceAdd = new SourceAdd(interestCategory.name(),
                    Category.Interests,
                    notification,
                    1,
                    enabled);
            sourceInterests.add(sourceAdd);
        }

        for(Category.socialMedia socialMediaCategory: socialMedia){
            SourceAdd sourceAdd = new SourceAdd(socialMediaCategory.name(),
                    Category.SocialMedia,
                    notification, 1, enabled);
            sourceInterests.add(sourceAdd);
        }

        /*
        These Elements are the News Elements which will be part of the Feed automatically
        */
        SourceAdd sourceFAZ = new SourceAdd(
                Category.news.FAZ.name(),
                Category.Newspaper,notification,
                2,
                enabled);

        SourceAdd sourceSpiegel = new SourceAdd(
                Category.news.Spiegel.name(),
                Category.Newspaper,
                notification,
               2,
                enabled);

        sourceInterests.add(sourceFAZ);
        sourceInterests.add(sourceSpiegel);
        /*
        Here, the array list from the For loop is passed to the DATA class
        and the initialisation process is terminated by editing
        the Boolean value in the Shared Preferences
        */
        data.setArrayList(sourceInterests);
    }
}