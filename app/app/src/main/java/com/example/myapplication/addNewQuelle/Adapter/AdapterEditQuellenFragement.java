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

public class AdapterEditQuellenFragement extends
        RecyclerView.Adapter<ViewholderEditQuellenFragement>{

    private ArrayList<Quellen> categories;
    private final QuelleSettingsChanged quelleSettingsChanged;

    public interface QuelleSettingsChanged{
        void changedQuelle(Quellen quellen);
    }


    public AdapterEditQuellenFragement(ArrayList<Quellen> categories,QuelleSettingsChanged qSC){
        this.categories = categories;
        this.quelleSettingsChanged = qSC;
    }

    @NonNull
    @Override
    public ViewholderEditQuellenFragement onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edit_quellen_icon,parent,false);

        return new ViewholderEditQuellenFragement(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderEditQuellenFragement holder, int position) {
        holder.bind(categories.get(position),quelleSettingsChanged,categories.size());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCategories(ArrayList<Quellen> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
