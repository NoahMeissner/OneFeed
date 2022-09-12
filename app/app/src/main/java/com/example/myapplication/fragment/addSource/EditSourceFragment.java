package com.example.myapplication.fragment.addSource;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.adapter.AdapterEditSourceFragment;
import com.example.myapplication.data.addSource.SourceAdd;

import java.util.ArrayList;
import java.util.Objects;

public class EditSourceFragment extends DialogFragment implements AdapterEditSourceFragment.SourceSettingsChanged {


    /*
    this method creates the EditSources fragment,
     which can be used to edit individual sources.
     */

    public interface SettingsChanges{
        void getChangedSourceArrayList(ArrayList<SourceAdd> sourceArrayList, Category categories);
    }


    private ArrayList<SourceAdd> settings = new ArrayList<>();
    private SourceAdd source;
    private final ArrayList<SourceAdd> recyclerArrayList = new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_edit_quellen, container, false);
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initUI(view);
        initRecyclerArrayList();
        initRecyclerView(view);
        return view;
    }



    private void initRecyclerArrayList() {
        if(Objects.equals(source.getName(), Category.ADDButton.name())){
            for(SourceAdd source: settings) {
                if (!source.getName().equals(Category.ADDButton.name()))
                    recyclerArrayList.add(source);
            }
            return;
        }
        recyclerArrayList.add(source);
    }

    // This Method initialise the Fragment items
    @SuppressLint("SetTextI18n")
    private void initUI(View view){
        ImageView imageView = view.findViewById(R.id.imageQuellenAdd);
        TextView textView = view.findViewById(R.id.headlineQuellenAdd);
        TextView underline = view.findViewById(R.id.textViewHeadlineQuellenAdd);
        imageView.setImageDrawable(source.getImage());
        if(!Objects.equals(source.getName(), Category.ADDButton.name())){
            textView.setText(source.getName());
        }
        else{
            textView.setText(getResources().getString(R.string.newSourceHeadline));
            underline.setText("");
        }
    }



    // This method initializes the RecyclerView for the settings
    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewEditQuellenFragement);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        layoutManager.canScrollVertically();
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);
        recyclerView.setLayoutManager(layoutManager);
        AdapterEditSourceFragment adapterEditSourceFragment = new AdapterEditSourceFragment(recyclerArrayList,this);
        recyclerView.setAdapter(adapterEditSourceFragment);
    }

    // With the following Methods it is possible to set the Materials for the Fragment Items


    @Override
    public void changedSource(SourceAdd source) {
            settings.remove(source);
            settings.add(source);
    }

    public void setSource(SourceAdd source) {
        this.source = source;
    }


    public void setSettings(ArrayList<SourceAdd> settings) {
        this.settings = settings;
    }
}