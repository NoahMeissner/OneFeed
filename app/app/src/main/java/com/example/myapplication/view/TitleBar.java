package com.example.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.myapplication.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class TitleBar extends CoordinatorLayout {

    private int pageContentResourceId;

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Read and initialize provided attributes
        initializeAttributes(context, attrs);

        // Inflate title-bar
        View.inflate(context, R.layout.component_title_bar, this);

        // Initialize content
        setTitle();
        setPageContent();
    }

    private void initializeAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TitleBar,
                0, 0);
        try {
            pageContentResourceId = a.getResourceId(
                    R.styleable.TitleBar_pageContentView, 0
            );
        } finally {
            a.recycle();
        }
    }

    private void setTitle() {
        CollapsingToolbarLayout toolbarLayout = this.findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(getDateTitle(LocalDateTime.now()));
    }

    private void setPageContent() {
        if (this.pageContentResourceId != 0) {
            ViewStub pageContent = (ViewStub) this.findViewById(R.id.page_content);
            pageContent.setLayoutResource(pageContentResourceId);
            pageContent.inflate();
        }
    }

    // Returns the day of the week in the default locale
    // Format: Samstag
    private String getDateTitle(LocalDateTime now) {
        return DateTimeFormatter.ofPattern("dd. ").format(now) +
                now.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

//    // Returns the title for the feed in the default locale
//    // Format: 20. August
//    private String getDayOfWeek(LocalDateTime now) {
//        return now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
//    }
}
