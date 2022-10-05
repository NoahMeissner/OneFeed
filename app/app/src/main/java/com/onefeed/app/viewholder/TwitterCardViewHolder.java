package com.onefeed.app.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onefeed.app.R;
import com.onefeed.app.adapter.NewsCardListAdapter;
import com.onefeed.app.data.card.TwitterCard;

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

    public void bind(TwitterCard data, NewsCardListAdapter.NewsOpenListener listener) {
        this.tweetContent.setText(data.getContent());
        this.authorName.setText(data.getAuthorName());
        this.authorHandle.setText(data.getAuthorUsername());
        this.authorProfileImage.setImageBitmap(data.getAuthorProfileImage());

        // Listener to open article on click
        itemView.setOnClickListener(l -> {
            listener.onOpen(data.getWebUrl());
        });
    }

    public static TwitterCardViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.component_card_tweet, parent,  false
        );
        return new TwitterCardViewHolder(view);
    }
}
