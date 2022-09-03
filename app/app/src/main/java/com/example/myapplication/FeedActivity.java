package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class FeedActivity extends AppCompatActivity {

    private TextView titleDate; // Format: 20. August
    private TextView titleDayOfWeek; // Format: Samstag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Title-bar
        setSupportActionBar(findViewById(R.id.toolbar));
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setContentScrimColor(getColor(R.color.md_theme_light_background));

//        this.titleDate = findViewById(R.id.feed_title_date);
//        this.titleDayOfWeek = findViewById(R.id.feed_title_day_of_week);
//
//        LocalDateTime now = LocalDateTime.now();
//        this.titleDate.setText(getTitleDate(now));
//        this.titleDayOfWeek.setText(getDayOfWeek(now));
    }
}