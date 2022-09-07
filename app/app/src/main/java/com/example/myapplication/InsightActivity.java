package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;

// Insight = Konsumverhalten
public class InsightActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight);

        // Title-bar
        setSupportActionBar(findViewById(R.id.app_bar_toolbar));

        // Toolbar
        this.toolbar = findViewById(R.id.app_bar_toolbar);
        this.toolbar.setNavigationOnClickListener(l -> {
            finish();
        });
    }
}