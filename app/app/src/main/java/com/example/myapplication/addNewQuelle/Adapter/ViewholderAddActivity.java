package com.example.myapplication.addNewQuelle.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.AnimateLinearLayout;
import com.example.myapplication.addNewQuelle.Categories;
import com.example.myapplication.addNewQuelle.Quellen;

import java.util.Objects;

public class ViewholderAddActivity extends RecyclerView.ViewHolder{

    private final ImageView imageView;
    private final TextView textView;
    private final LinearLayout linearLayout;
    private boolean click;
    private boolean setAnimation;
    private final AnimateLinearLayout animateLinearLayout = itemView.findViewById(R.id.frameLayout_icons_Quellen);

    public ViewholderAddActivity(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.quellenImage);
        textView = itemView.findViewById(R.id.quelleText);
        linearLayout = itemView.findViewById(R.id.deleteLayout);
        linearLayout.setVisibility(View.GONE);
    }

    public void bind(final Quellen quellen,final AdapterListAddActivity.OnItemClickListener listener) {
        textView.setText("");
        if(quellen.getAnimation()&&!Objects.equals(quellen.getName(), Categories.ADDButton.name())){
                animateLinearLayout.animateText();
                linearLayout.setVisibility(View.VISIBLE);
                setAnimation = true;
            }
            if(!quellen.getAnimation()&&setAnimation){
                animateLinearLayout.stopAnimation();
                linearLayout.setVisibility(View.GONE);
            }
            if(!Objects.equals(quellen.getName(), Categories.ADDButton.name())){
                textView.setText(quellen.getName());
            }
            imageView.setImageDrawable(quellen.getImage());
            itemView.setOnClickListener( view -> listener.onItemClick(quellen));
    }

    public void bindlong(Quellen quellen, AdapterListAddActivity.longItemClickListener longItemClickListener) {
        itemView.setOnLongClickListener(view -> {
            if (Objects.equals(quellen.getName(), Categories.ADDButton.name())){
                return false;
            }
            if(!click){
                longItemClickListener.onLongClick(quellen);
                if(!Objects.equals(quellen.getName(), Categories.ADDButton.name())){
                    linearLayout.setVisibility(View.VISIBLE);
                }
                click = true;
                return true;
            }
            click = false;
            longItemClickListener.onLongClick(quellen);
            return false;
        });
    }
}


