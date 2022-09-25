package com.example.myapplication.activity.onboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.activity.FeedActivity;
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.onBoard.SetSourceObjects;
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
    private boolean interestsAreInitialized = false;
    private SharedPreferences pref;


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
        InitialData data;
        if(!interestsAreInitialized){
            data = new InitialData(this);
            setSources(
                    data.getSelectedInterests(),
                    data.getSelectedSocialMedia(),
                    data
            );
            SharedPreferences.Editor editPreferences = pref.edit();
            editPreferences.putBoolean(Category.initial.InterestsAreInitialised.name(), true);
            editPreferences.apply();
        }
    }

    /*
    This Method will set all Sources which were selected in the Initial Process
     */
    private void setSources(ArrayList<String> interests,
                            ArrayList<String> socialMedia,
                            InitialData data){
        /*
        This Set Source Object hands all Source over
         */
        int i = data.getSelectedInterests().size();
        SetSourceObjects sourceObjects = new SetSourceObjects(data,notification);
        sourceObjects.setSocialMediaList(socialMedia);
        sourceObjects.setInterestsList(interests);
        sourceObjects.setNews();
        sourceObjects.addToDataBase();
    }
}