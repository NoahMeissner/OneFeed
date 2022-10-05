package com.onefeed.app.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onefeed.app.R;
import com.onefeed.app.viewholder.ViewHolderAddActivity;
import com.onefeed.app.data.addSource.SourceAdd;

import java.util.ArrayList;

public class AdapterListAddActivity extends RecyclerView.Adapter<ViewHolderAddActivity> {

    public interface OnItemClickListener{
        void onItemClick(SourceAdd source);
    }

    public interface longItemClickListener{
        void onLongClick(SourceAdd source);
    }

    private ArrayList<SourceAdd> sourceArrayList;
    private final OnItemClickListener listener;
    private final longItemClickListener longItemClickListener;

    public AdapterListAddActivity(OnItemClickListener listener,
                                  longItemClickListener longItemClickListener,
                                  ArrayList<SourceAdd> sourceArrayList){
        this.listener=listener;
        this.longItemClickListener = longItemClickListener;
        this.sourceArrayList =sourceArrayList;
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public ViewHolderAddActivity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.component_icons_quellen,
                parent,
                false);
        return new ViewHolderAddActivity(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAddActivity holder, int position) {
        holder.bind(sourceArrayList.get(position), listener);
        holder.bindLong(sourceArrayList.get(position), longItemClickListener);
    }

    @Override
    public int getItemCount() {
        return sourceArrayList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSourceArrayList(ArrayList<SourceAdd> sourceArrayList){
        this.sourceArrayList = sourceArrayList;
        for(SourceAdd sourceAdd :sourceArrayList){
            Log.d(sourceAdd.getName(), String.valueOf(sourceAdd.getCategories()));
        }
        notifyDataSetChanged();
    }
}
