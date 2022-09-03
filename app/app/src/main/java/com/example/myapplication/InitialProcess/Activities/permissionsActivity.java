package com.example.myapplication.InitialProcess.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.myapplication.InitialProcess.FragementsUI.KonsumanalyseFragement;
import com.example.myapplication.InitialProcess.FragementsUI.NotifiactionFragement;
import com.example.myapplication.InitialProcess.InitialData;
import com.example.myapplication.R;

import java.util.ArrayList;

public class permissionsActivity extends AppCompatActivity {

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

    private void getIntentInformation(){
        Intent intent = getIntent();
        interests = intent.getStringArrayListExtra(String.valueOf(InitialData.interestsArrayList));
    }

    private void initFragement() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayoutPermissions, KonsumanalyseFragement.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("backstack")
                .commit();
    }

    private void initUI() {
        Button buttonYes= findViewById(R.id.buttonYes);
        Button buttonNo=findViewById(R.id.buttonNo);
        setListener(buttonYes,true);
        setListener(buttonNo,false);


    }

    private void setListener(Button button,boolean result){
        button.setOnClickListener(view -> {
            switch (fragmentManager.getBackStackEntryCount()){
            case 1:
                this.consumptionanalysis=result;
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayoutPermissions, NotifiactionFragement.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("backtack")
                        .commit();

                return;
            case 2:
                this.notifications=result;
                Intent intent = new Intent(this, LoadingActivity.class);
                intent.putExtra(String.valueOf(InitialData.consumptionAnalysePermission),consumptionanalysis);
                intent.putExtra(String.valueOf(InitialData.notificationAnalysePermission),notifications);
                intent.putExtra(String.valueOf(InitialData.interestsArrayList),interests);
                //@TODO Social Media Array Liste übergeben
                //intent.putExtra(String.valueOf(InitialData.socialMediaArrayList),null);
                startActivity(intent);
        }
        });
    }
}

