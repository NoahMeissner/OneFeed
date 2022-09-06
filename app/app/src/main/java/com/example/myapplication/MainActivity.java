package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.InitialProcess.Activities.InitialActivity;
import com.example.myapplication.addNewQuelle.ADDActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {

            Intent intent = new Intent(this, ADDActivity.class);
            startActivity(intent);

        setContentView(R.layout.activity_main);
    }


}