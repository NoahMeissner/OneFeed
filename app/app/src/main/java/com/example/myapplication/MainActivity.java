package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.myapplication.Activities.InitialActivity;
import com.example.myapplication.Activities.addNewQuelle.ADDActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> interessen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        if (!InitialInformation.initialApplication) {
            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        presentData();

    }

    private void presentData() {
        Log.d("NotificationsPermissions", String.valueOf(InitialInformation.notificationPermission));
        Log.d("ConsumptionPermissions", String.valueOf(InitialInformation.consumptionanalysePermission));
        Log.d("Interests", String.valueOf(InitialInformation.interestsList.size()));

    }
}