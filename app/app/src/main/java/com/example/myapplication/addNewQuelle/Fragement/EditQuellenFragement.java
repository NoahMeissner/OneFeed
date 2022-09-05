package com.example.myapplication.addNewQuelle.Fragement;

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
import com.example.myapplication.addNewQuelle.Categories;
import com.example.myapplication.addNewQuelle.adapter.AdapterEditQuellenFragement;

import java.util.ArrayList;

public class EditQuellenFragement extends DialogFragment {

    /*
    this method creates the EditSources fragment,
     which can be used to edit individual sources.
     */

    private String name;
    private String category;
    private Drawable drawable;
    private ArrayList<String[]> settings = new ArrayList<>();
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
        initUI(view);
        initcategories();
        initRecyclerView(view);
        return view;
    }

    // This Method initialise the Fragement items
    private void initUI(View view){
        ImageView imageView = view.findViewById(R.id.imageQuellenAdd);
        TextView textView = view.findViewById(R.id.headlineQuellenAdd);
        TextView underline = view.findViewById(R.id.textViewHeadlineQuellenAdd);
        imageView.setImageDrawable(drawable);
        if(name != Categories.ADDButton.name()){
            textView.setText(name);
        }
        else{
            textView.setText("");
            underline.setText("Quellen");
        }
    }

    //@TODO Hernach Löschen nicht mehr gebraucht
    private void initcategories() {
        if(name == Categories.ADDButton.name()){
            if(category== Categories.Newspaper.name()){

            }
            if(category== Categories.Interessen.name()){

            }
            if(category==Categories.SocialMedia.name()){

            }
        }
        else{
            settings.add(new String[]{"Notification", String.valueOf(wahr)});
            settings.add(new String[]{"Enabeld", String.valueOf(falsch)});
            settings.add(new String[]{"Konsumanalyse", String.valueOf(falsch)});
        }
    }

    // This method initializes the RecyclerView for the settings
    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewEditQuellenFragement);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.canScrollVertically();
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);
        recyclerView.setLayoutManager(layoutManager);
        AdapterEditQuellenFragement adapterEditQuellenFragement = new AdapterEditQuellenFragement(settings);
        recyclerView.setAdapter(adapterEditQuellenFragement);
    }

    // With the following Methods it is possible to set the Materials for the Fragement Items
    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setSettings(ArrayList<String[]> settings) {
        this.settings = settings;
    }
}