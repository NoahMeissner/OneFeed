package com.example.myapplication.addNewQuelle;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class EditQuellenFragement extends DialogFragment {

    String kategorie;
    Drawable drawable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_edit_quellen, container, false);
        ImageView imageView = view.findViewById(R.id.imageQuellenAdd);
        TextView textView = view.findViewById(R.id.headlineQuellenAdd);
        imageView.setImageDrawable(drawable);
        textView.setText(kategorie);

        return view;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}