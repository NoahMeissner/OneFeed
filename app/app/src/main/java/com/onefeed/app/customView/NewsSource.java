package com.onefeed.app.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.onefeed.app.R;

public class NewsSource extends LinearLayout {

    private String name;
    private int iconResourceId;

    public NewsSource(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Read and initialize provided attributes
        initializeAttributes(context, attrs);

        // Inflate layout
        View.inflate(context, R.layout.component_news_source, this);

        // Initialise name
        TextView name = this.findViewById(R.id.source_name);
        name.setText(this.name);

        // Initialise icon
        ImageView icon = this.findViewById(R.id.source_icon);
        icon.setImageResource(iconResourceId);
    }

    private void initializeAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NewsSource,
                0, 0);
        try {
            name = a.getString(R.styleable.NewsSource_name);
            iconResourceId = a.getResourceId(R.styleable.NewsSource_sourceIcon, 0);
        } finally {
            a.recycle();
        }
    }
}
