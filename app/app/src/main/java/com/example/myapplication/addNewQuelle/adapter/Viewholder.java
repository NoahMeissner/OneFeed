package com.example.myapplication.addNewQuelle.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.Quellen;

public class Viewholder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView textView;

    public Viewholder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.quellenImage);
        textView = itemView.findViewById(R.id.quelleText);
    }


    public void bind(final Quellen quellen,final Adapter.OnItemClickListener listener) {
        textView.setText(quellen.getName());
        imageView.setImageDrawable(quellen.getImage());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(quellen);
            }
        });
    }
}


