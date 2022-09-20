package com.example.myapplication.fragment.addSource;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.adapter.AdapterEditSourceFragment;
import com.example.myapplication.data.addSource.SourceAdd;
import com.example.myapplication.database.GetData;

import java.util.ArrayList;
import java.util.Objects;

public class EditSourceFragment extends DialogFragment
        implements AdapterEditSourceFragment.SourceSettingsChanged {


    /*
    this method creates the EditSources fragment,
     which can be used to edit individual sources.
     */

    /*
    Constants
     */
    private final GetData getData;
    private EditSourceFragmentChanges dataChanged;
    private final ArrayList<SourceAdd> selectedHashMap;
    private final SourceAdd source;
    private final ArrayList<SourceAdd> recyclerArrayList = new ArrayList<>();
    private ArrayList<SourceAdd> fullList = new ArrayList<>();

    /*
    Constructor
     */
    public EditSourceFragment(SourceAdd source,
                    ArrayList<SourceAdd> selectedHashMap,
                    EditSourceFragmentChanges dataChanged,
                    GetData getdata){
        this.source = source;
        this.selectedHashMap = selectedHashMap;
        this.dataChanged = dataChanged;
        this.getData = getdata;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_edit_quellen, container, false);
        Objects.requireNonNull(getDialog())
                .getWindow()
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init(view);
        return view;
    }

    /*
    This Method init all important UI Elements
     */
    private void init(View view){
        initUI(view);
        initRecyclerArrayList();
        initRecyclerView(view);
    }


    /*
    This method init the Recycler view ArrayList
     */
    private void initRecyclerArrayList() {
        if(Objects.equals(source.getName(), Category.ADDButton.name())){
            for(SourceAdd source: combineArrayLists()) {
                if (!source.getName().equals(Category.ADDButton.name()))
                    recyclerArrayList.add(source);
            }
            return;
        }
        recyclerArrayList.add(source);
    }

    /*
    This Method combines the Full Array List with the selected Hash Map
     */
    private ArrayList<SourceAdd> combineArrayLists() {
        for(SourceAdd sourceActive : selectedHashMap){
            fullList.removeIf(fullSource -> fullSource.getName().equals(sourceActive.getName()));
        }
        ArrayList<SourceAdd> result = new ArrayList<>(selectedHashMap);

        if(fullList != null){
            result.addAll(fullList);
        }
        return result;
    }
    /*
    This Method initialise the Fragment items
     */
    @SuppressLint("SetTextI18n")
    private void initUI(View view){
        ImageView imageView = view.findViewById(R.id.imageQuellenAdd);
        TextView textView = view.findViewById(R.id.headlineQuellenAdd);
        TextView underline = view.findViewById(R.id.textViewHeadlineQuellenAdd);
        if(!Objects.equals(source.getName(), Category.ADDButton.name())){
            textView.setText(source.getName());
            //@TODO Bilder müssen stehen
            Drawable drawable = getResources().getDrawable(source.getImageRessourceID(),getActivity().getTheme());
            imageView.setImageDrawable(drawable);
        }
        else{
            imageView.setImageDrawable(source.getImage());
            textView.setText(getResources().getString(R.string.newSourceHeadline));
            underline.setText("");
        }
    }


    /*
    This Method init the Recycler view to Show the List of Settings
     */
    private void initRecyclerView(View view) {
        // init the RecyclerView and the layout Manager
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewEditQuellenFragement);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        layoutManager.canScrollVertically();
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);

        params.setMargins(0, 0, 0, 0);
        recyclerView.setLayoutManager(layoutManager);

        // Set the Adapter and overhand the Adapter to the Recycler View
        AdapterEditSourceFragment adapterEditSourceFragment = new AdapterEditSourceFragment(
                recyclerArrayList,source.getName(),this);

        recyclerView.setAdapter(adapterEditSourceFragment);
    }

    /*
    This Method is important to add a new Source to the DataBase
     */
    private void insertNewSource(SourceAdd changedSource) {
        SharedPreferences preferences = requireContext()
                .getSharedPreferences(getResources()
                .getString(R.string.initProcesBoolean), 0);

        if(changedSource.isEnabled()){
            deleteItem(changedSource);
            return;
        }

        SourceAdd sourceAdd = new SourceAdd(
                (changedSource.getName()),(changedSource.getCategories()),
                (preferences.getBoolean(Category.initial.Notification.name(),false)),
                2, true);

        selectedHashMap.add(sourceAdd);
        getData.InsertSource(sourceAdd);
        dataChanged.dataHasChanged(true, changedSource);
        onStop();
    }




    /*
    If someone wants to delete a Source he can use the Switch in the EditSource Fragment
    To Change the Settings in the DataBase you need this Method
     */
    private void deleteItem(SourceAdd source) {
        selectedHashMap.remove(source);
        getData.removeSource(source);
        dataChanged.dataHasChanged(false, source);
        onStop();
    }

    /*
    This Method will give the User the whole List of Sources to compare which
    sources he had not apply
     */
    private ArrayList<SourceAdd> updateList(ArrayList<SourceAdd> fullList) {
        for (SourceAdd sourceAdd:fullList){
            sourceAdd.setEnabled(false);
        }
        return fullList;
    }

    public void setDataChanged(EditSourceFragmentChanges dataChanged) {
        this.dataChanged = dataChanged;
    }

    public void setFullList(ArrayList<SourceAdd> fullList){
        this.fullList = updateList(fullList);
    }

    @Override
    public void changedSource(SourceAdd changedSource) {
        if(source.getName().equals(Category.ADDButton.name())){
            insertNewSource(changedSource);
            return;
        }
        boolean notification = changedSource.isNotification();
        changedSource.setNotification(!notification);
        selectedHashMap.remove(changedSource);
        selectedHashMap.add(changedSource);
        getData.removeSource(changedSource);
        getData.InsertSource(changedSource);
    }

    public interface EditSourceFragmentChanges {
        void dataHasChanged(Boolean b, SourceAdd sourceAdd);
    }
}