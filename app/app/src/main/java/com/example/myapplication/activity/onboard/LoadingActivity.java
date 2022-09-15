package com.example.myapplication.activity.onboard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.activity.FeedActivity;
import com.example.myapplication.data.addSource.AddActivityIcons;
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.addSource.SourceAdd;
import com.example.myapplication.database.Data;

import java.util.ArrayList;
import java.util.Objects;

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


    /*
    This method calls all methods of the class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSharedPreferences();
        initUi();
        setUpInterests();
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
        Intent intent = new Intent(getBaseContext(), FeedActivity.class);
        startActivity(intent);
    }


    /*
    This method converts the categories that have been selected in the on boarding
     process into category objects.
     */
    private void setUpInterests(){
        //@TODO DATABASE
        AddActivityIcons addActivityIcons = new AddActivityIcons();
        ArrayList<SourceAdd> sourceInterests = new ArrayList<>();
        Data data = new Data();

        if(interestsAreInitialized && data.getSelectedInterests()!= null){
            ArrayList<Category.interests> interests = data.getSelectedInterests();

            /*
            In the For loop, the selected interests are converted
             into objects and passed to the array list
             */

            for(Category.interests interestCategory: interests){
                @SuppressLint("UseCompatLoadingForDrawables") SourceAdd sourceAdd = new SourceAdd(
                        interestCategory.name(),
                        getDrawable(Objects.requireNonNull(addActivityIcons
                                        .getInterestsHashMap()
                                        .get(interestCategory))),
                        Category.Interests);

                sourceAdd.setEnabled(enabled);
                sourceAdd.setNotification(notification);
                sourceInterests.add(sourceAdd);
            }

            /*
            Here, the array list from the For loop is passed to the DATA class
             and the initialisation process is terminated by editing
              the Boolean value in the Shared Preferences
             */
            data.setInterests(sourceInterests);
            SharedPreferences.Editor editPreferences = pref.edit();
            editPreferences.putBoolean(Category.initial.InterestsAreInitialised.name(), true);
            editPreferences.apply();
        }
    }
}