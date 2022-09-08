package com.example.myapplication.addNewQuelle.Fragement;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.Categories;
import com.example.myapplication.addNewQuelle.Adapter.AdapterEditQuellenFragement;
import com.example.myapplication.addNewQuelle.Quellen;

import java.util.ArrayList;

public class EditQuellenFragement extends DialogFragment implements AdapterEditQuellenFragement.QuelleSettingsChanged {


    /*
    this method creates the EditSources fragment,
     which can be used to edit individual sources.
     */

    public interface SettingsChanges{
        void getChangedQuellenArrayList(ArrayList<Quellen> quellenArrayList,Categories categories);
    }


    private ArrayList<Quellen> settings = new ArrayList<>();
    private Quellen quellen;
    private ArrayList<Quellen> recyclerArrayList = new ArrayList<>();
    private Button safeButton;

    public EditQuellenFragement (SettingsChanges settingsChanges){
        //this.settingsChanges = settingsChanges;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_edit_quellen, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initUI(view);
        initRecyclerArrayList();
        initRecyclerView(view);
        //initButton(view);
        return view;
    }



    private void initRecyclerArrayList() {
        if(quellen.getName()==Categories.ADDButton.name()){
            for(Quellen quellen: settings) {
                if (quellen.getName() != Categories.ADDButton.name())
                    recyclerArrayList.add(quellen);
            }
            return;
        }
        recyclerArrayList.add(quellen);
    }

    // This Method initialise the Fragement items
    private void initUI(View view){
        ImageView imageView = view.findViewById(R.id.imageQuellenAdd);
        TextView textView = view.findViewById(R.id.headlineQuellenAdd);
        TextView underline = view.findViewById(R.id.textViewHeadlineQuellenAdd);
        imageView.setImageDrawable(quellen.getImage());
        if(quellen.getName() != Categories.ADDButton.name()){
            textView.setText(quellen.getName());
        }
        else{
            textView.setText("Neue Quelle");
            underline.setText("");
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
        AdapterEditQuellenFragement adapterEditQuellenFragement = new AdapterEditQuellenFragement(recyclerArrayList,this);
        recyclerView.setAdapter(adapterEditQuellenFragement);
    }

    // With the following Methods it is possible to set the Materials for the Fragement Items


    @Override
    public void changedQuelle(Quellen quellen) {
            settings.remove(quellen);
            settings.add(quellen);
    }

    public void setQuellen(Quellen quellen) {
        this.quellen = quellen;
    }


    public void setSettings(ArrayList<Quellen> settings) {
        this.settings = settings;
    }
}