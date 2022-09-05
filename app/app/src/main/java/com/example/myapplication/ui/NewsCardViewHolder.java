package com.example.myapplication.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.NewsCard;

public class NewsCardViewHolder extends RecyclerView.ViewHolder {
    private final TextView titleView;

    public NewsCardViewHolder(View itemView) {
        super(itemView);
        this.titleView = itemView.findViewById(R.id.article_card_title);
    }

    public void bind(NewsCard data) {
        titleView.setText(data.getTitle());
    }

    static NewsCardViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.component_card_article, parent,  false
        );
        return new NewsCardViewHolder(view);
    }
}
