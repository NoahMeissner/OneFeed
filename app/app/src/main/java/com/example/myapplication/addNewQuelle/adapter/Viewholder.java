package com.example.myapplication.addNewQuelle.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class Viewholder extends RecyclerView.ViewHolder{

    public Viewholder(@NonNull View itemView) {
        super(itemView);
        ImageView picture = itemView.findViewById(R.id.quellenImage);
        TextView textView = itemView.findViewById(R.id.quelleText);
    }


}
