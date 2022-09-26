package com.example.myapplication.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.card.TwitterCard;

public class TwitterCardViewHolder extends RecyclerView.ViewHolder {
    private final TextView tweetContent;
    private final TextView authorName;
    private final TextView authorHandle;
    private final ImageView authorProfileImage;

    public TwitterCardViewHolder(View itemView) {
        super(itemView);
        this.authorName = itemView.findViewById(R.id.tweet_card_author_name);
        this.authorHandle = itemView.findViewById(R.id.tweet_card_author_handle);
        this.tweetContent = itemView.findViewById(R.id.article_card_title);
        this.authorProfileImage = itemView.findViewById(R.id.tweet_card_profile_picture);
    }

    public void bind(TwitterCard data) {
        this.tweetContent.setText(data.getContent());
        this.authorName.setText(data.getAuthorName());
        this.authorHandle.setText(data.getAuthorUsername());
        this.authorProfileImage.setImageBitmap(data.getAuthorProfileImage());
    }

    public static TwitterCardViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.component_card_tweet, parent,  false
        );
        return new TwitterCardViewHolder(view);
    }
}
