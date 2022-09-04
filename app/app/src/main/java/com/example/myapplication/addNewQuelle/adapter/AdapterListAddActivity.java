package com.example.myapplication.addNewQuelle.adapter;

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

    public interface OnItemClickListener{
        void onItemClick(Quellen quellen);
    }

    private ArrayList<Quellen> quellenArrayList;
    private OnItemClickListener listener;

    public AdapterListAddActivity(OnItemClickListener listener, ArrayList<Quellen> quellenArrayList){
        this.listener=listener;
        this.quellenArrayList=quellenArrayList;
    }

    @NonNull
    @Override
    public ViewholderAddActivity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.icons_quellen,parent,false);
        return new ViewholderAddActivity(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderAddActivity holder, int position) {
        holder.bind(quellenArrayList.get(position),listener);
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
