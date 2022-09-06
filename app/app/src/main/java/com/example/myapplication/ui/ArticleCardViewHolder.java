package com.example.myapplication.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.card.ArticleCard;

public class ArticleCardViewHolder extends RecyclerView.ViewHolder {
    private final TextView titleView;

    public ArticleCardViewHolder(View itemView) {
        super(itemView);
        this.titleView = itemView.findViewById(R.id.article_card_title);
    }

    public void bind(ArticleCard data) {
        titleView.setText(data.getTitle());
    }

    static ArticleCardViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.component_card_article, parent,  false
        );
        return new ArticleCardViewHolder(view);
    }
}
