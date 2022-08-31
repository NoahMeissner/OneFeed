package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.myapplication.R;
import com.example.myapplication.animations.BubbleAnimation;

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



}