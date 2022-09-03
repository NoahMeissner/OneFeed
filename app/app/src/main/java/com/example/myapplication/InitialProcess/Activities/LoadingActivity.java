package com.example.myapplication.InitialProcess.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;

import com.example.myapplication.R;

public class LoadingActivity extends AppCompatActivity {



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