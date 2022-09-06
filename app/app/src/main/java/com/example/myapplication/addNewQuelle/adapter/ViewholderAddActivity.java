package com.example.myapplication.addNewQuelle.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.AnimateLinearLayout;
import com.example.myapplication.addNewQuelle.Categories;
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
        if(quellen.getName()!= Categories.ADDButton.name()){
            textView.setText(quellen.getName());

        }
        else textView.setText("");
        imageView.setImageDrawable(quellen.getImage());
        itemView.setOnClickListener( view -> listener.onItemClick(quellen));
    }

    public void bindlong(Quellen quellen, AdapterListAddActivity.longItemClickListener longItemClickListener) {
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longItemClickListener.onLongClick(quellen);
                if(quellen.getName()!= Categories.ADDButton.name()){
                    AnimateLinearLayout animateLinearLayout = view.findViewById(R.id.linearLayoutQuellenIcons);
                    animateLinearLayout.animateText();
                }
                return true;
            }
        });
    }
}


