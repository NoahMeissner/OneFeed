package com.example.myapplication.InitialProcess.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.FeedActivity;

public class LoadingActivity extends AppCompatActivity {

    /*
    This Activity Displays the Loading Activity
     */


    private boolean isInitialized =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
    }

    private void initUi() {
        if(!isInitialized){
            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent(getBaseContext(), FeedActivity.class);
        startActivity(intent);
    }


    //@TODO Fehlermeldung einbauen wenn nicht mit Internet verbunden
}