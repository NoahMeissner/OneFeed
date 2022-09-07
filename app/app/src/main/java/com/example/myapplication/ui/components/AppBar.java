package com.example.myapplication.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class AppBar extends CoordinatorLayout {

    private String title;
    private int menuResourceId;
    private int pageContentResourceId;

    private MaterialToolbar materialToolbar;

    public AppBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Read and initialize provided attributes
        initializeAttributes(context, attrs);

        // Inflate layout
        View.inflate(context, R.layout.component_app_bar, this);

        // Initialize views
        initViews();

        // Initialize content
        setTitle();
        setMenu();
        setPageContent();
    }

    private void initViews() {
        this.materialToolbar = this.findViewById(R.id.app_bar_toolbar);
    }

    private void setMenu() {
        materialToolbar.inflateMenu(this.menuResourceId);
    }

    private void initializeAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AppBar,
                0, 0);
        try {
            this.title = a.getString(R.styleable.AppBar_title);
            this.menuResourceId = a.getResourceId(
                    R.styleable.AppBar_menu,
                    R.menu.empty
            );
            this.pageContentResourceId = a.getResourceId(
                    R.styleable.AppBar_pageContentView,
                    R.layout.view_news_cards_recycler
            );
        } finally {
            a.recycle();
        }
    }

    private void setTitle() {
        if (this.title == null) {
            // Default behavior: Current date as title
            materialToolbar.setTitle(getDateTitle(LocalDateTime.now()));
        } else {
            // If provided: Custom title from string
            materialToolbar.setTitle(this.title);
        }
    }

    private void setPageContent() {
        if (this.pageContentResourceId != 0) {
            ViewStub pageContent = (ViewStub) this.findViewById(R.id.app_bar_page_content);
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
