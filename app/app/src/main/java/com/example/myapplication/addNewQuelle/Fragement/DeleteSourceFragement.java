package com.example.myapplication.addNewQuelle.Fragement;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;

public class DeleteSourceFragement extends DialogFragment {


    private String headline;
    private Drawable drawable;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.delete_source_fragement, container, false);
        TextView textView = view.findViewById(R.id.deleteSourceHeadline);
        ImageView imageView = view.findViewById(R.id.deleteSourceImage);
        textView.setText(headline);
        imageView.setImageDrawable(drawable);
        return view;
    }

    public void setImage(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }
}
