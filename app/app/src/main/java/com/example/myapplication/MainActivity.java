package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.InitialProcess.Activities.InitialActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {

            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);

        setContentView(R.layout.activity_main);
    }


}