package com.example.myapplication.addNewQuelle.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.Quellen;

public class ViewholderAddActivity extends RecyclerView.ViewHolder{

    private final ImageView imageView;
    private final TextView textView;

    public ViewholderAddActivity(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.quellenImage);
        textView = itemView.findViewById(R.id.quelleText);
    }


    public void bind(final Quellen quellen,final AdapterListAddActivity.OnItemClickListener listener) {
        textView.setText(quellen.getName());
        imageView.setImageDrawable(quellen.getImage());
        itemView.setOnClickListener( view -> listener.onItemClick(quellen));
    }
}


