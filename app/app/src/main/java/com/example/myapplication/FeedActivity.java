package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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

        this.titleDate = findViewById(R.id.feed_title_date);
        this.titleDayOfWeek = findViewById(R.id.feed_title_day_of_week);

        LocalDateTime now = LocalDateTime.now();
        this.titleDate.setText(getTitleDate(now));
        this.titleDayOfWeek.setText(getDayOfWeek(now));
    }

    // Returns the title for the feed in the default locale
    // Format: 20. August
    private String getDayOfWeek(LocalDateTime now) {
        return now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    // Returns the day of the week in the default locale
    // Format: Samstag
    private String getTitleDate(LocalDateTime now) {
        return DateTimeFormatter.ofPattern("dd. ").format(now) +
                now.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }
}