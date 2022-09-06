package com.example.myapplication.ui;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DiffUtil.DiffResult;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.card.ArticleCard;

import java.util.ArrayList;
import java.util.List;

public class NewsCardListAdapter extends RecyclerView.Adapter<ArticleCardViewHolder> {
    private List<ArticleCard> articleCards = new ArrayList<>();

    public void updateItems(List<ArticleCard> data) {
        NewsDiffCallback diffCallback = new NewsDiffCallback(
                this.articleCards, data
        );
        DiffResult callback = DiffUtil.calculateDiff(diffCallback);
        callback.dispatchUpdatesTo(this);
        this.articleCards.clear();
        this.articleCards.addAll(data);
    }

    @Override
    public int getItemViewType(int position) {
        // Source: https://stackoverflow.com/a/26245463
        return position % 2 * 2;
    }

    @Override
    public ArticleCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ArticleCardViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleCardViewHolder holder, int position) {
        ArticleCard articleCard = articleCards.get(position);
        holder.bind(articleCard);
    }

    @Override
    public int getItemCount() {
        return this.articleCards.size();
    }

    static class NewsDiffCallback extends DiffUtil.Callback {
        List<ArticleCard> oldCards;
        List<ArticleCard> newCards;

        public NewsDiffCallback(List<ArticleCard> oldCards, List<ArticleCard> newCards) {
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
