package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.Activities.InitialActivity;
import com.example.myapplication.Activities.addNewQuelle.ADDActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public boolean setUpApplication = true;
    ArrayList<String> interessen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        if (setUpApplication) {
            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button3);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), ADDActivity.class);
            startActivity(intent);
        });
    }

    public void setSetUpApplication(boolean setUpApplication) {
        this.setUpApplication = setUpApplication;
    }
}