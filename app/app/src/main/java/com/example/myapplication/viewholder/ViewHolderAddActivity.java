package com.example.myapplication.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AdapterListAddActivity;
import com.example.myapplication.animation.addSource.AnimateLinearLayout;
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.addSource.SourceAdd;

import java.util.Objects;

public class ViewHolderAddActivity extends RecyclerView.ViewHolder{

    private final ImageView imageView;
    private final TextView textView;
    private final LinearLayout linearLayout;
    private boolean longSourceClick;
    private boolean setAnimation;
    private final AnimateLinearLayout animateLinearLayout = itemView
            .findViewById(R.id.frameLayout_icons_Quellen);

    public ViewHolderAddActivity(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.quellenImage);
        textView = itemView.findViewById(R.id.quelleText);
        linearLayout = itemView.findViewById(R.id.deleteLayout);
        linearLayout.setVisibility(View.GONE);

    }

    public void bind(final SourceAdd source, final AdapterListAddActivity.OnItemClickListener listener) {
        imageView.setImageDrawable(source.getImage());
        itemView.setOnClickListener( view -> listener.onItemClick(source));
        if(source.getAnimation()&&!Objects.equals(source.getName(), Category.ADDButton.name())){
                animateLinearLayout.animateItems();
                linearLayout.setVisibility(View.VISIBLE);
                setAnimation = true;
            }
            if(!source.getAnimation()&&setAnimation){
                animateLinearLayout.stopAnimation();
                linearLayout.setVisibility(View.GONE);
            }
            if(!Objects.equals(source.getName(), Category.ADDButton.name())){
                textView.setText(source.getName());
            }
            if(Objects.equals(source.getName(), Category.ADDButton.name())){
                textView.setText("");
            }
    }

    public void bindLong(SourceAdd source, AdapterListAddActivity.
            longItemClickListener longItemClickListener) {

        itemView.setOnLongClickListener(view -> {
            if (Objects.equals(source.getName(), Category.ADDButton.name())){
                return false;
            }
            if(!longSourceClick){
                longItemClickListener.onLongClick(source);
                if(!Objects.equals(source.getName(), Category.ADDButton.name())){
                    linearLayout.setVisibility(View.VISIBLE);
                }
                longSourceClick = true;
                return true;
            }
            longSourceClick = false;
            longItemClickListener.onLongClick(source);
            return false;
        });
    }
}


