package com.example.myapplication.ui;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DiffUtil.DiffResult;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.card.ArticleCard;
import com.example.myapplication.data.card.NewsCard;
import com.example.myapplication.data.card.TwitterCard;

import java.util.ArrayList;
import java.util.List;

public class NewsCardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<NewsCard> cards = new ArrayList<>();

    public void updateItems(List<NewsCard> data) {
        NewsDiffCallback diffCallback = new NewsDiffCallback(
                this.cards, data
        );
        DiffResult callback = DiffUtil.calculateDiff(diffCallback);
        callback.dispatchUpdatesTo(this);
        this.cards.clear();
        this.cards.addAll(data);
    }

    @Override
    public int getItemViewType(int position) {
        // Source: https://stackoverflow.com/a/26245463
        if (cards.get(position).getClass() == ArticleCard.class) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: return ArticleCardViewHolder.create(parent);
            case 1: return TwitterCardViewHolder.create(parent);
        }

        return ArticleCardViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ArticleCardViewHolder articleHolder = (ArticleCardViewHolder) holder;
                ArticleCard articleCard = (ArticleCard) cards.get(position);
                articleHolder.bind(articleCard);
                break;
            case 1:
                TwitterCardViewHolder twitterHolder = (TwitterCardViewHolder) holder;
                TwitterCard twitterCard = (TwitterCard) cards.get(position);
                twitterHolder.bind(twitterCard);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return this.cards.size();
    }

    static class NewsDiffCallback extends DiffUtil.Callback {
        List<NewsCard> oldCards;
        List<NewsCard> newCards;

        public NewsDiffCallback(List<NewsCard> oldCards, List<NewsCard> newCards) {
            this.oldCards = oldCards;
            this.newCards = newCards;
        }

        @Override
        public int getOldListSize() {
            return oldCards.size();
        }

        @Override
        public int getNewListSize() {
            return newCards.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return this.oldCards.get(oldItemPosition) == this.newCards.get(newItemPosition);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return this.oldCards.get(oldItemPosition)
                    .equals(this.newCards.get(newItemPosition));
        }
    }
}
