package com.example.myapplication.ui;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DiffUtil.DiffResult;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.NewsCard;

import java.util.ArrayList;
import java.util.List;

public class NewsCardListAdapter extends RecyclerView.Adapter<NewsCardViewHolder> {
    private List<NewsCard> newsCards = new ArrayList<>();

    public void updateItems(List<NewsCard> data) {
        NewsDiffCallback diffCallback = new NewsDiffCallback(
                this.newsCards, data
        );
        DiffResult callback = DiffUtil.calculateDiff(diffCallback);
        callback.dispatchUpdatesTo(this);
        this.newsCards.clear();
        this.newsCards.addAll(data);
    }

    @Override
    public NewsCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return NewsCardViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsCardViewHolder holder, int position) {
        NewsCard newsCard = newsCards.get(position);
        holder.bind(newsCard);
    }

    @Override
    public int getItemCount() {
        return this.newsCards.size();
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
