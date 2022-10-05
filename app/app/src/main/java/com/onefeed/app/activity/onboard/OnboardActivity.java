package com.onefeed.app.activity.onboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.onefeed.app.data.addSource.Constants;
import com.onefeed.app.database.InitialData;
import com.onefeed.app.fragment.onboard.InterestsFragment;
import com.onefeed.app.fragment.onboard.SocialMediaFragment;
import com.onefeed.app.fragment.onboard.WelcomeFragment;
import com.onefeed.app.R;

import java.util.ArrayList;

public class OnboardActivity extends AppCompatActivity implements InterestsFragment.OnDataPass, SocialMediaFragment.GetSelectedSocialMedia {

    /*
    This activity is responsible for the entire setup process.
     Here the individual setup steps and the further development of Permissions Activities are completed
     */

     private FragmentManager fragmentManager;
     private ArrayList<Constants.interests> interestsList = new ArrayList<>();
     private final ArrayList<Constants.socialMedia> socialMediaList = new ArrayList<>();
     private InitialData data;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_initial);
            data = new InitialData(getApplicationContext());
            initUI();
        }

    // Set up the Fragment Manager
    private void initUI() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.initial_frame_layout, WelcomeFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("")
                .commit();
        initButton();
    }

    // This Method ist responsible for the forwarding to the different set up steps
    private void initButton() {
        Button button = findViewById(R.id.buttonFurther);
        button.setOnClickListener(view -> {
            switch (fragmentManager.getBackStackEntryCount()){
                case 1:
                    fragmentManager.beginTransaction()
                            .replace(R.id.initial_frame_layout, InterestsFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("")
                            .commit();
                    return;
                case 2:
                    fragmentManager.beginTransaction()
                            .replace(R.id.initial_frame_layout, SocialMediaFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("")
                            .commit();
                    transmitData();
                    return;
                case 3:
                    Intent intent = new Intent(
                            OnboardActivity.this,
                            PermissionsActivity.class);
                    getSocialMediaData();
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    private void getSocialMediaData() {
            data.setSelectSocialMedia(socialMediaList);
    }


    // This method grabs the Interests ArrayList from the Interests Query
    @Override
    public void onDataPass(ArrayList<Constants.interests> interestsList) {
        this.interestsList = interestsList;
    }

    private void transmitData(){
        data.setSelectedInterests(interestsList);
    }

    @Override
    public void getSelectedSocialMedia(Constants.socialMedia socialMedia) {
        socialMediaList.add(socialMedia);
    }
}
