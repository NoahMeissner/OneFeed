package com.example.myapplication.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class NewsSourceComponent extends LinearLayout {

    private String name;
    private int iconResourceId;

    public NewsSourceComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Read and initialize provided attributes
        initializeAttributes(context, attrs);

        // Inflate layout
        View.inflate(context, R.layout.component_news_source, this);

        // Initialise name
        TextView name = this.findViewById(R.id.fragment_source_name);
        name.setText(this.name);

        // Initialise icon
        ImageView icon = this.findViewById(R.id.fragment_source_icon);
        icon.setImageResource(iconResourceId);
    }

    private void initializeAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NewsSourceComponent,
                0, 0);
        try {
            name = a.getString(R.styleable.NewsSourceComponent_name);
            iconResourceId = a.getResourceId(R.styleable.NewsSourceComponent_sourceIcon, 0);
        } finally {
            a.recycle();
        }
    }
}
