package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.FragementsUI.KonsumanalyseFragement;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class permissionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        initUI();
        initFragement();
    }

    private void initFragement() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayoutPermissions, KonsumanalyseFragement.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    private void initUI() {
        Button buttonYes= findViewById(R.id.buttonYes);
        Button buttonNo=findViewById(R.id.buttonNo);
        //@TODO Zeige welche Button geklickt wurde
        Intent intent = new Intent(permissionsActivity.this, MainActivity.class);
        buttonYes.setOnClickListener(view -> startActivity(intent));
        buttonNo.setOnClickListener(view -> startActivity(intent));
    }




}