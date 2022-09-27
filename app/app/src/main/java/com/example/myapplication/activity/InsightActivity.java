package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.insight.InsightViewModel;
import com.example.myapplication.data.insight.NewsReadEntry;
import com.example.myapplication.data.insight.ReadingDay;
import com.example.myapplication.fragment.analysis.PermissionsDialogFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.slider.Slider;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

// Insight = Konsumverhalten
public class InsightActivity extends AppCompatActivity {

    private BarChart chart;
    private InsightViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight);

        // Viewmodel
        this.viewModel = new ViewModelProvider(this).get(InsightViewModel.class);

        // Title-bar
        setSupportActionBar(findViewById(R.id.app_bar_toolbar));

        // Toolbar
        MaterialToolbar toolbar = findViewById(R.id.app_bar_toolbar);
        // @TODO von Noah
        toolbar.setNavigationOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), FeedActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            finish();
        });

        // Chart
        initializeChart();
        initializeChartControls();

        // Settings
        initializeSettings();
        initialFragement();
    }

    /*
    This Method initial the Permissions Dialog Fragment,
     which asks the User to accept the consumption analysis
     */
    private void initialFragement() {
        if(!viewModel.getLimitationIsEnabled().getValue()){
            PermissionsDialogFragment permissionsDialogFragement = new PermissionsDialogFragment();
            permissionsDialogFragement.show(getSupportFragmentManager(),"");

            // Listener for the response so the acitivty refreshes
            permissionsDialogFragement.setResultListener(enabled -> {
                viewModel.setLimitationIsEnabled(getBaseContext(), enabled);
            });
        }
    }

    private void initializeSettings() {
        MaterialSwitch limitArticlesSwitch = findViewById(R.id.insight_limit_articles_switch);
        Slider limitArticlesSlider = findViewById(R.id.insight_limit_articles_slider);
        TextView limitArticlesDescription = findViewById(R.id.insight_limit_articles_description);

        // Update view on value changes
        viewModel.getLimitationIsEnabled().observe(this, enabled -> {
            limitArticlesSwitch.setChecked(enabled);
            // Disable controls
            limitArticlesSlider.setEnabled(enabled);
            limitArticlesDescription.setEnabled(enabled);
        });
        viewModel.getArticlesPerDay().observe(this, articlesPerDay -> {
            if (viewModel.getLimitationIsEnabled().getValue()) {
                limitArticlesSlider.setValue(articlesPerDay);
            }
        });

        // Update values on input
        limitArticlesSlider.addOnChangeListener((slider, value, fromUser) -> {
            viewModel.setArticleLimitation(this, (int)value);
        });
        limitArticlesSwitch.setOnCheckedChangeListener((button, newValue) -> {
            viewModel.setLimitationIsEnabled(this, newValue);
        });
    }

    private void initializeChartControls() {
        // Views
        MaterialButton chartWeekButton = findViewById(R.id.insight_chart_toggle_week);
        MaterialButton chartMonthButton = findViewById(R.id.insight_chart_toggle_month);
        MaterialButton chartYearButton = findViewById(R.id.insight_chart_toggle_year);

        // Listeners
        LocalDateTime now = LocalDateTime.now();
        int currentDayOfYear = now.getDayOfYear();

        // Week button
        chartWeekButton.setChecked(true);
        chartWeekButton.setOnClickListener(l -> activateWeekView(now, currentDayOfYear));

        // Month button
        chartMonthButton.setOnClickListener(l -> activateMonthView(now, currentDayOfYear));

        // Year button
        chartYearButton.setOnClickListener(l -> activateYearView(now));
    }

    private void activateYearView(LocalDateTime now) {
        float barWidth = ResourcesCompat.getFloat(
                this.getResources(), R.dimen.insight_chart_bar_width_year
        );

        int amountDaysInYear = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR);

        // Adjust the axis min & max
        // no padding (value too large / small for chart because out of current year)
        this.chart.getXAxis().setAxisMinimum(1); // barwidth for padding
        this.chart.getXAxis().setAxisMaximum(amountDaysInYear);

        // Format the labels according to months
        this.chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                // Chart x-axis granularity has to be set to 1 for this to work:
                // MPAndroidChart iterates through every x-value so float values should not
                // be a possibility!
                LocalDateTime providedDate = now.withDayOfYear((int)value);
                return providedDate.getMonth().getDisplayName(
                        TextStyle.SHORT_STANDALONE, Locale.getDefault()
                );
            }
        });

        if (this.chart.getXAxis().getLabelCount() != 12) {
            this.chart.getXAxis().setLabelCount(12);
            if (this.chart.getBarData() != null) {
                this.chart.getBarData().setBarWidth(barWidth);
            }
        }
        refreshChart();
    }

    private void activateMonthView(LocalDateTime now, int currentDayOfYear) {
        float barWidth = ResourcesCompat.getFloat(this.getResources(), R.dimen.insight_chart_bar_width_month);

        int currentDayOfMonth = now.getDayOfMonth();
        int startOfCurrentMonth = currentDayOfYear - currentDayOfMonth;
        int amountDaysInMonth = Calendar.getInstance().getActualMaximum(Calendar.DATE);

        // Adjust the axis min & max
        float chartPaddingLeft = barWidth;
        float chartPaddingRight = barWidth;
        if (now.getMonth() == Month.JANUARY) chartPaddingLeft = 0;
        if (now.getMonth() == Month.DECEMBER) chartPaddingRight = 0;

        this.chart.getXAxis().setAxisMinimum(startOfCurrentMonth - chartPaddingLeft);
        this.chart.getXAxis().setAxisMaximum(startOfCurrentMonth + amountDaysInMonth + chartPaddingRight);

        // Format the labels according to days in a month
        this.chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                // Chart x-axis granularity has to be set to 1 for this to work:
                // MPAndroidChart iterates through every x-value so float values should not
                // be a possibility!
                return String.valueOf(now.withDayOfYear((int)value).getDayOfMonth());
            }
        });

        // Displaying 30 dates would not fit the chart space
        if (this.chart.getXAxis().getLabelCount() != 15) {
            this.chart.getXAxis().setLabelCount(15);
            if (this.chart.getBarData() != null) {
                this.chart.getBarData().setBarWidth(barWidth);
            }
        }
        refreshChart();
    }

    private void activateWeekView(LocalDateTime now, int currentDayOfYear) {
        float barWidth = ResourcesCompat.getFloat(this.getResources(), R.dimen.insight_chart_bar_width_week);

        int currentDayOfWeek = now.getDayOfWeek().getValue();
        int startOfCurrentWeek = currentDayOfYear - currentDayOfWeek + 1;

        // Adjust the axis min & max
        float chartPaddingLeft = barWidth;
        float chartPaddingRight = barWidth;
        if (now.getMonth() == Month.JANUARY && now.getDayOfMonth() <= 7) chartPaddingLeft = 0;
        if (now.getMonth() == Month.DECEMBER && now.getDayOfMonth() >= 25) chartPaddingRight = 0;

        this.chart.getXAxis().setAxisMinimum(startOfCurrentWeek - chartPaddingLeft);
        this.chart.getXAxis().setAxisMaximum(startOfCurrentWeek + 6 + chartPaddingRight); // 6 more days

        // Format the labels according to weekdays
        this.chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                // Chart x-axis granularity has to be set to 1 for this to work:
                // MPAndroidChart iterates through every x-value so float values should not
                // be a possibility!
                try {
                    DayOfWeek dayOfWeek = DayOfWeek.of((int) value - startOfCurrentWeek + 1);
                    return dayOfWeek.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault());
                } catch (RuntimeException exception) {
                    // Possibly an MPANdroid bug: value provided for the formatter is > than the
                    // previously set minimum and maximum of the charts
                    // Workaround: Catch exception for these cases...
                    return "";
                }
            }
        });

        if (this.chart.getXAxis().getLabelCount() != 7) {
            if (this.chart.getBarData() != null) {
                this.chart.getBarData().setBarWidth(barWidth);
            }
            this.chart.getXAxis().setLabelCount(7);
        }

        refreshChart();
    }

    private void refreshChart() {
        this.chart.fitScreen();
        this.chart.invalidate();
    }

    private void initializeChart() {
        this.chart = findViewById(R.id.insight_chart);

        // Data
        this.viewModel.getNewsReadList().observe(this, data -> {
            // Refresh chart on any change to the dataset
            setBarData(data);
        });

        // Axis settings
        // - Disable unused axis
        this.chart.getAxisLeft().setEnabled(false);

        // - Dashed lines
        this.chart.getAxisRight().enableGridDashedLine(4, 4, 0);
        this.chart.getXAxis().enableGridDashedLine(4, 4, 0);

        // - x axis
        this.chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        this.chart.getXAxis().setGranularity(1);

        // y axis
        this.chart.getAxisRight().setAxisMinimum(0);

        // Chart settings
        this.chart.getLegend().setEnabled(false);
        this.chart.getDescription().setEnabled(false);

        // Viewport settings
        // - viewport has to be adjusted after the data has been loaded (setData) to work
        this.chart.fitScreen();
        this.chart.setVisibleXRange(0, 7);
        this.chart.setTouchEnabled(false);
        this.chart.setFitBars(true);

        activateWeekView(LocalDateTime.now(), LocalDateTime.now().getDayOfYear());

        // Initial load
        setBarData(viewModel.getNewsReadList().getValue());
    }

    private void setBarData(List<NewsReadEntry> data) {
        if (data == null) return;

        List<BarEntry> entries = viewModel.convertIntoBarEntries(data);

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setValueTextColor(MaterialColors.getColor(chart, com.google.android.material.R.attr.colorOnSurface));
        dataSet.setColor(MaterialColors.getColor(chart, androidx.transition.R.attr.colorPrimary));
        // - axis dependency is required because the axis is being adjusted
        dataSet.setAxisDependency(chart.getAxisRight().getAxisDependency());

        // Bar settings
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.3f);

        chart.setData(barData);
        chart.notifyDataSetChanged();
    }
}