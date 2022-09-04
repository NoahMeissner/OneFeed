package com.example.myapplication.addNewQuelle;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.adapter.AdapterEditQuellenFragement;

import java.util.ArrayList;

public class EditQuellenFragement extends DialogFragment {

    String kategorie;
    Drawable drawable;
    private ArrayList<String[]> categories = new ArrayList<>();
    private boolean wahr = true;
    private boolean falsch = false;



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
        initcategories();
        initUI(view);
        return view;
    }

    private void initcategories() {
        categories.add(new String[]{"Notification", String.valueOf(wahr)});
        categories.add(new String[]{"Enabeld", String.valueOf(falsch)});
    }

    private void initUI(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewEditQuellenFragement);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.canScrollVertically();
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);
        recyclerView.setLayoutManager(layoutManager);
        AdapterEditQuellenFragement adapterEditQuellenFragement = new AdapterEditQuellenFragement(categories);
        recyclerView.setAdapter(adapterEditQuellenFragement);
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setCategories(ArrayList<String[]> categories) {
        this.categories = categories;
    }
}