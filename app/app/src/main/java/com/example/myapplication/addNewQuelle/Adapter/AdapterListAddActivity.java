package com.example.myapplication.addNewQuelle.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.Quellen;

import java.util.ArrayList;

public class AdapterListAddActivity extends RecyclerView.Adapter<ViewholderAddActivity> {

    int id = R.layout.icons_quellen;

    public interface OnItemClickListener{
        void onItemClick(Quellen quellen);
    }

    public interface longItemClickListener{
        void onLongClick(Quellen quellen);
    }

    private ArrayList<Quellen> quellenArrayList;
    private final OnItemClickListener listener;
    private final longItemClickListener longItemClickListener;

    public AdapterListAddActivity(OnItemClickListener listener, longItemClickListener longItemClickListener, ArrayList<Quellen> quellenArrayList){
        this.listener=listener;
        this.longItemClickListener = longItemClickListener;
        this.quellenArrayList=quellenArrayList;
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public ViewholderAddActivity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(id,parent,false);

        return new ViewholderAddActivity(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderAddActivity holder, int position) {
        holder.bind(quellenArrayList.get(position), listener);
        holder.bindlong(quellenArrayList.get(position), longItemClickListener);

    }

    @Override
    public int getItemCount() {
        return quellenArrayList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setQuellenArrayList(ArrayList<Quellen> quellenArrayList){
        this.quellenArrayList= quellenArrayList;
        notifyDataSetChanged();
    }
}
