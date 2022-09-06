package com.example.myapplication.InitialProcess.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import com.example.myapplication.R;

public class LoadingActivity extends AppCompatActivity {

    /*
    This Activity Displays the Loading Activity
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
    }

    private void initUi() {
        Intent intent = new Intent(this, InitialActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_loading);
    }


    //@TODO Fehlermeldung einbauen wenn nicht mit Internet verbunden
}