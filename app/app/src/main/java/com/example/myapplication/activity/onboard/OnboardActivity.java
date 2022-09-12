package com.example.myapplication.activity.onboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.fragment.onboard.InterestsFragment;
import com.example.myapplication.fragment.onboard.SocialMediaFragment;
import com.example.myapplication.fragment.onboard.WelcomeFragment;
import com.example.myapplication.data.onboard.OnboardingUserData;
import com.example.myapplication.R;

import java.util.ArrayList;

public class OnboardActivity extends AppCompatActivity implements InterestsFragment.OnDataPass{

    /*
    This activity is responsible for the entire setup process.
     Here the individual setup steps and the further development of Permissions Activities are completed
     */

     private FragmentManager fragmentManager;
     private ArrayList<String> interests = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_initial);
            initUI();
        }

    // Set up the Fragment Manager
    private void initUI() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, WelcomeFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("")
                .commit();
        initButton();
    }

    // This Method ist responsible for the forwarding to the different set up steps
    private void initButton() {
        Button button = findViewById(R.id.buttonWeiter);
        button.setOnClickListener(view -> {
            switch (fragmentManager.getBackStackEntryCount()){
                case 1:
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, InterestsFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("")
                            .commit();
                    return;
                case 2:
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, SocialMediaFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("")
                            .commit();
                    return;
                case 3:
                    Intent intent = new Intent(
                            OnboardActivity.this,
                            PermissionsActivity.class);

                    intent.putExtra(String.valueOf(OnboardingUserData.interestsArrayList),interests);
                    startActivity(intent);
            }
        });
    }


    // This method grabs the Interests ArrayList from the Interests Query
    @Override
    public void onDataPass(ArrayList<String> interestsList) {
        interests = interestsList;
    }
}
