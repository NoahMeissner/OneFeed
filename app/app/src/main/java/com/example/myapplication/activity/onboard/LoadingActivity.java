package com.example.myapplication.activity.onboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.activity.FeedActivity;
import com.example.myapplication.data.addSource.Category;

public class LoadingActivity extends AppCompatActivity {

    /*
    This Activity Displays the Loading Activity
     */


    private boolean isInitialized =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSharedPreferences();
        initUi();
    }

    private void initSharedPreferences() {
        SharedPreferences pref = getSharedPreferences(getResources()
                .getString(R.string.initProcesBoolean), 0);

        isInitialized = pref.getBoolean(Category.initial.Process.name(),false);
    }

    private void initUi() {
        if(!isInitialized){
            Intent intent = new Intent(this, OnboardActivity.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent(getBaseContext(), FeedActivity.class);
        startActivity(intent);
    }


    //@TODO Fehlermeldung einbauen wenn nicht mit Internet verbunden
}