package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.FragementsUI.InteressenFragement;
import com.example.myapplication.FragementsUI.SocialMediaFragement;
import com.example.myapplication.FragementsUI.WillkommenFragement;
import com.example.myapplication.InitialInformation;
import com.example.myapplication.R;

import java.util.ArrayList;

public class InitialActivity extends AppCompatActivity implements InteressenFragement.OnDataPass{

     private FragmentManager fragmentManager;
     private ArrayList<String> interests = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_initial);
            initUI();
        }

    private void initUI() {
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
        button.setOnClickListener(view -> {
            switch (fragmentManager.getBackStackEntryCount()){
                case 1:
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, InteressenFragement.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("Willkommen")
                            .commit();
                    return;
                case 2:
                    checkData();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, SocialMediaFragement.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("Willkommen")
                            .commit();
                    return;
                case 3:
                    Intent intent = new Intent(InitialActivity.this, permissionsActivity.class);
                    InitialInformation.interestsList=interests;
                    startActivity(intent);
            }
        });
    }

    private void  checkData(){
            for(String s: interests){
                System.out.println(s);
            }
    }


    @Override
    public void onDataPass(ArrayList<String> interessenList) {
        interests = interessenList;
    }
}
