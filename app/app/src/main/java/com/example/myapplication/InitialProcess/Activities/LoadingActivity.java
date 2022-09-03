package com.example.myapplication.InitialProcess.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;

import com.example.myapplication.R;

public class LoadingActivity extends AppCompatActivity {

    private final Point size = new Point();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
    }

    private void initUi() {
        setContentView(R.layout.activity_loading);
    }


    //@TODO Fehlermeldung einbauen wenn nicht mit Internet verbunden
}