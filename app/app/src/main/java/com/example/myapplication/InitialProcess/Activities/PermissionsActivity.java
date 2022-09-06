package com.example.myapplication.InitialProcess.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.FeedActivity;
import com.example.myapplication.InitialProcess.FragementsUI.Consumption_permission_fragement;
import com.example.myapplication.InitialProcess.FragementsUI.Notification_permission_fragement;
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
    private boolean consumptionanalysis = false;
    private ArrayList<String> interests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        getIntentInformation();
        initFragement();
        initUI();
    }

    // this method queries the information passed through the intent
    private void getIntentInformation(){
        Intent intent = getIntent();
        interests = intent.getStringArrayListExtra(String.valueOf(InitialData.interestsArrayList));
    }

    // This Method initialise the fragement Manager for the first Fragement
    private void initFragement() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayoutPermissions, Consumption_permission_fragement.class, null)
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
                this.consumptionanalysis=result;
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayoutPermissions, Notification_permission_fragement.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("backtack")
                        .commit();

                return;
            case 2:
                this.notifications=result;
                setIntent(consumptionanalysis,notifications);
        }
        });
    }

    // the Method hands over all Informations of the Set Up Process to the Intent and the new Activity
    private void setIntent(boolean consumptionanalysis, boolean notifications){
        Intent intent = new Intent(this, FeedActivity.class);
        intent.putExtra(String.valueOf(InitialData.consumptionAnalysePermission),consumptionanalysis);
        intent.putExtra(String.valueOf(InitialData.notificationAnalysePermission),notifications);
        intent.putExtra(String.valueOf(InitialData.interestsArrayList),interests);
        //@TODO Social Media Array Liste übergeben
        //intent.putExtra(String.valueOf(InitialData.socialMediaArrayList),null);
        startActivity(intent);
    }
}

