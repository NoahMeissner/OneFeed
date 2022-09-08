package com.example.myapplication.InitialProcess.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.FeedActivity;
import com.example.myapplication.R;

public class LoadingActivity extends AppCompatActivity {

    /*
    This Activity Displays the Loading Activity
     */


    private boolean isInitalised =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
    }

    private void initUi() {
        if(!isInitalised){
            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent(getBaseContext(), FeedActivity.class);
        startActivity(intent);
    }


    //@TODO Fehlermeldung einbauen wenn nicht mit Internet verbunden
}