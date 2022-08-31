package com.example.myapplication.Activities.addNewQuelle.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.addNewQuelle.Quellen;
import com.example.myapplication.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<Viewholder> {

    private ArrayList<Quellen> quellenArrayList;
    private ImageView imageView;
    private TextView name;

    public RecyclerViewAdapter(){
        this.quellenArrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createViewHolderForType(parent,viewType);
    }

    private Viewholder createViewHolderForType(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.icons_quellen,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        initItems(holder);
        Quellen quellen = quellenArrayList.get(position);
        imageView.setImageDrawable(quellen.getImage());
        name.setText(quellen.getName());
    }

    private void initItems(Viewholder holder) {
        imageView = holder.itemView.findViewById(R.id.quellenImage);
        name = holder.itemView.findViewById(R.id.quelleText);
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
