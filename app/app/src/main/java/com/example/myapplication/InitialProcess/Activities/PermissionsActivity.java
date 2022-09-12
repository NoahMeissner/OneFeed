package com.example.myapplication.InitialProcess.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.FeedActivity;
import com.example.myapplication.InitialProcess.FragementsUI.ConsumptionPermissionFragment;
import com.example.myapplication.InitialProcess.FragementsUI.NotificationPermissionFragment;
import com.example.myapplication.InitialProcess.InitialData;
import com.example.myapplication.R;

import java.util.ArrayList;

public class PermissionsActivity extends AppCompatActivity {

    /*
    During the set-up process, this activity asks for various information that requires the user's consent.
    In addition, she passes on all the information she received through the initial activity.
     */

    private FragmentManager fragmentManager;
    private boolean notifications = false;
    private boolean consumptionAnalyse = false;
    private ArrayList<String> interests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        getIntentInformation();
        initFragment();
        initUI();
    }

    // this method queries the information passed through the intent
    private void getIntentInformation(){
        Intent intent = getIntent();
        interests = intent.getStringArrayListExtra(String.valueOf(InitialData.interestsArrayList));
    }

    // This Method initialise the Fragment Manager for the first Fragment
    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayoutPermissions, ConsumptionPermissionFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("backstack")
                .commit();
    }

    // This Method initialise all Items from the Activity and sets the Button Listener
    private void initUI() {
        Button buttonYes= findViewById(R.id.buttonYes);
        Button buttonNo=findViewById(R.id.buttonNo);
        setListener(buttonYes,true);
        setListener(buttonNo,false);


    }

    /*
    On the one hand, this method receives the information from the user, which is important for setting up the app.
     In addition, it creates the change between the individual fragments.
     */
    private void setListener(Button button,boolean result){
        button.setOnClickListener(view -> {
            switch (fragmentManager.getBackStackEntryCount()){
            case 1:
                this.consumptionAnalyse =result;
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayoutPermissions, NotificationPermissionFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();

                return;
            case 2:
                this.notifications=result;
                setIntent(consumptionAnalyse,notifications);
        }
        });
    }

    // the Method hands over all Information of the Set Up Process to the Intent and the new Activity
    private void setIntent(boolean consumptionAnalysis, boolean notifications){
        Intent intent = new Intent(this, FeedActivity.class);
        intent.putExtra(String.valueOf(InitialData.consumptionAnalysePermission),consumptionAnalysis);
        intent.putExtra(String.valueOf(InitialData.notificationAnalysePermission),notifications);
        intent.putExtra(String.valueOf(InitialData.interestsArrayList),interests);
        //@TODO Social Media Arraylist übergeben
        startActivity(intent);
    }
}

