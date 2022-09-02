package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.myapplication.FragementsUI.KonsumanalyseFragement;
import com.example.myapplication.FragementsUI.NotifiactionFragement;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class permissionsActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private boolean notifications = false;
    private boolean consumptionanalysis = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        initFragement();
        initUI();
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
        setListener(buttonYes,false);
        setListener(buttonNo,true);


    }

    private void setListener(Button button,boolean result){
        button.setOnClickListener(view -> {
            switch (fragmentManager.getBackStackEntryCount()){
            case 1:
                this.notifications=result;
                Log.d("Nn","NO/yES");
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayoutPermissions, NotifiactionFragement.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("backtack")
                        .commit();

                return;
            case 2:
                this.consumptionanalysis=result;
                Log.d("konsumanalyse", String.valueOf(consumptionanalysis));
                Log.d("*", "********");
                Log.d("Notification", String.valueOf(notifications));
                Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
        }
        });
    }
}

