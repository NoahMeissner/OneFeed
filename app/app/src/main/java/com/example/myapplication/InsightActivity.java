package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;

import com.example.myapplication.data.insight.ReadingDay;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.color.MaterialColors;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

// Insight = Konsumverhalten
public class InsightActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    BarChart chart;

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

        // Chart
        this.chart = findViewById(R.id.insight_chart);

        ReadingDay exampleDayMon = new ReadingDay(DayOfWeek.MONDAY, 12);
        ReadingDay exampleDayTue = new ReadingDay(DayOfWeek.TUESDAY, 0);
        ReadingDay exampleDayThu = new ReadingDay(DayOfWeek.THURSDAY, 24);
        ReadingDay exampleDayWed = new ReadingDay(DayOfWeek.WEDNESDAY, 11);
        ReadingDay exampleDayFri = new ReadingDay(DayOfWeek.FRIDAY, 8);
        ReadingDay exampleDaySat = new ReadingDay(DayOfWeek.SATURDAY, 0);
        ReadingDay exampleDaySun = new ReadingDay(DayOfWeek.SUNDAY, 0);


        List<ReadingDay> dataObjects = Arrays.asList(
                exampleDayMon, exampleDayTue, exampleDayThu, exampleDayWed, exampleDayFri
//                exampleDaySat, exampleDaySun
        );

        List<BarEntry> entries = new ArrayList<BarEntry>();
        for (ReadingDay data : dataObjects) {
            entries.add(new BarEntry(data.getDayOfWeek().getValue(), data.getAmountArticlesRead()));
        }
        BarDataSet dataSet = new BarDataSet(entries, "Gelesene Artikel");
        dataSet.setColor(MaterialColors.getColor(chart, androidx.transition.R.attr.colorPrimary));
        dataSet.setAxisDependency(chart.getAxisRight().getAxisDependency());

        // Bar settings
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.3f);

        // Axis settings
        this.chart.getAxisLeft().setEnabled(false);
        this.chart.getAxisRight().setAxisMinimum(0);
        this.chart.getAxisRight().enableGridDashedLine(4, 4, 0);
        this.chart.getXAxis().enableGridDashedLine(4, 4, 0);
        this.chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        this.chart.getXAxis().setGranularity(1);
        this.chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                // Chart x-axis granularity has to be set to 1 for this to work:
                // MPAndroidChart iterates through every x-value so float values should not
                // be a possibility!
                DayOfWeek dow = DayOfWeek.of((int) value);
                return dow.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault());
            }
        });

        // Description settings
//        Description description = new Description();
//        description.setText("Gelesene Artikel");
//        description.setTextSize(16);
//        description.setPosition(0, 0);
//        description.setPosition(this.chart.getCenter().x, 0);
//        description.setTextAlign(Paint.Align.RIGHT);
//        description.setEnabled(false);
//        description.setTextColor(MaterialColors.getColor(chart, com.google.android.material.R.attr.colorOnSurfaceVariant));

        // Chart settings
        this.chart.setData(barData);
//        this.chart.setDescription(description);
        this.chart.getLegend().setEnabled(false);
        this.chart.getDescription().setEnabled(false);

        // Viewport settings
        this.chart.fitScreen();
        this.chart.setVisibleXRange(0, 7);
        this.chart.setTouchEnabled(false);
        this.chart.setFitBars(true);

        this.chart.invalidate();
    }
}