package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.FragementsUI.InteressenFragement;
import com.example.myapplication.FragementsUI.SocialMediaFragement;
import com.example.myapplication.FragementsUI.WillkommenFragement;
import com.example.myapplication.R;

public class InitialActivity extends AppCompatActivity {


        private int counter =1;
        private FragmentManager fragmentManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_initial);
            initUI(savedInstanceState);
        }

    private void initUI(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, WillkommenFragement.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("Willkommen")
                .commit();
        initButton();
    }

    private void initButton() {
        Button button = findViewById(R.id.buttonWeiter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (fragmentManager.getBackStackEntryCount()){
                    case 1:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frameLayout, InteressenFragement.class, null)
                                .setReorderingAllowed(true)
                                .addToBackStack("Willkommen")
                                .commit();
                        return;
                    case 2:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frameLayout, SocialMediaFragement.class, null)
                                .setReorderingAllowed(true)
                                .addToBackStack("Willkommen")
                                .commit();
                        return;
                    case 3:
                        Intent intent = new Intent(InitialActivity.this, permissionsActivity.class);
                        startActivity(intent);
                        return;
                }
            }
        });
    }


}
