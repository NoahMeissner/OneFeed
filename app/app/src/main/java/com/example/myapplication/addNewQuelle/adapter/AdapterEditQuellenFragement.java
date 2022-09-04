package com.example.myapplication.addNewQuelle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterEditQuellenFragement extends RecyclerView.Adapter<ViewholderEditQuellenFragement> {

    private ArrayList<String[]> categories;


    public AdapterEditQuellenFragement(ArrayList<String[]> categories){
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewholderEditQuellenFragement onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_quellen_icon,parent,false);
        return new ViewholderEditQuellenFragement(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderEditQuellenFragement holder, int position) {
        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
