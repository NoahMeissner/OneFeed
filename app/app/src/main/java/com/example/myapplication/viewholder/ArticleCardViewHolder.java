package com.example.myapplication.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.card.ArticleCard;

public class ArticleCardViewHolder extends RecyclerView.ViewHolder {
    private final TextView titleView;
    private final ImageView imageView;

    public ArticleCardViewHolder(View itemView) {
        super(itemView);
        this.titleView = itemView.findViewById(R.id.article_card_title);
        this.imageView = itemView.findViewById(R.id.article_card_image);
    }

    public void bind(ArticleCard data) {
        titleView.setText(data.getTitle());
        // Todo: Implement cards without image
        if (data.getImage() != null) {
            this.imageView.setImageBitmap(data.getImage());
        }
    }

    public static ArticleCardViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.component_card_article, parent,  false
        );
        return new ArticleCardViewHolder(view);
    }
}
