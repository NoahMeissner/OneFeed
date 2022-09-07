package com.example.myapplication.addNewQuelle.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.Quellen;

import java.util.ArrayList;

public class AdapterEditQuellenFragement extends RecyclerView.Adapter<ViewholderEditQuellenFragement> {

    private ArrayList<Quellen> categories;
    private QuelleSettingsChanged quelleSettingsChanged;

    public interface QuelleSettingsChanged{
        void changedQuelle(Quellen quellen);
    }


    public AdapterEditQuellenFragement(ArrayList<Quellen> categories,QuelleSettingsChanged quelleSettingsChanged){
        this.categories = categories;
        this.quelleSettingsChanged = quelleSettingsChanged;
    }

    @NonNull
    @Override
    public ViewholderEditQuellenFragement onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_quellen_icon,parent,false);
        return new ViewholderEditQuellenFragement(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderEditQuellenFragement holder, int position) {
        holder.bind(categories.get(position),quelleSettingsChanged,categories.size(),position);
    }

    public void setCategories(ArrayList<Quellen> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
