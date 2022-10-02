package com.example.myapplication.fragment.addSource;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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
import com.example.myapplication.data.addSource.Constants;
import com.example.myapplication.data.addSource.UiElements;
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
    // These Constants will be called by the Constructor
    private final SourceAdd source;
    private final ArrayList<SourceAdd> selectedHashMap;
    private final GetData getData;
    private final ArrayList<String> fullList;

    // This Constant will be the Array List which fills the Recycler View
    private final ArrayList<SourceAdd> recyclerArrayList = new ArrayList<>();

    // Shared Preferences are important to edit saved Data
    private SharedPreferences preferences;

    // The AddActivityIcons are important to get the Pictures
    private final UiElements uiElements = new UiElements();


    /*
    Constructor
     */
    public EditSourceFragment( SourceAdd source,
                              ArrayList<SourceAdd> selectedHashMap,
                              GetData getdata){
        this.source = source;
        this.selectedHashMap = selectedHashMap;
        this.getData = getdata;
        fullList = uiElements.getArrayListHashMap().get(source.getCategories());
        uiElements.initialPictureHashMap();
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
        preferences = requireContext()
                .getSharedPreferences(requireActivity().getResources()
                        .getString(R.string.init_process_boolean), 0);
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
        if(Objects.equals(source.getName(), Constants.ADDButton.name())){
            for(SourceAdd source: combineArrayLists()) {
                if (!source.getName().equals(Constants.ADDButton.name()))
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
            fullList.removeIf(fullSource -> fullSource.equals(sourceActive.getName()));
        }
        ArrayList<SourceAdd> result = new ArrayList<>(selectedHashMap);

        if(fullList != null){
            for(String name: fullList){
                SourceAdd sourceAdd = new SourceAdd(
                        name,
                        source.getCategories(),
                        preferences.getBoolean(Constants.initial.Notification.name(), false),
                        Objects.requireNonNull(uiElements.getPictureId(name)),
                        false);

                result.add(sourceAdd);
            }
        }
        return result;
    }

    /*
    This Method initialise the Fragment UI Elements
     */
    @SuppressLint("SetTextI18n")
    private void initUI(View view){
        ImageView imageView = view.findViewById(R.id.imageQuellenAdd);
        TextView textView = view.findViewById(R.id.headlineQuellenAdd);
        TextView underline = view.findViewById(R.id.textViewHeadlineQuellenAdd);
        if(!Objects.equals(source.getName(), Constants.ADDButton.name())){
            textView.setText(source.getName());
            imageView.setImageResource(source.getImageRessourceID());
        }
        else{
            textView.setText(getResources().getString(R.string.news_source_headline));
            imageView.setImageResource(R.drawable.add);
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

        if(changedSource.isEnabled()){
            deleteItem(changedSource);
            onStop();
            return;
        }
        SourceAdd sourceAdd = new SourceAdd(
                (changedSource.getName()),(changedSource.getCategories()),
                (preferences.getBoolean(Constants.initial.Notification.name(),false)),
                getImageID(changedSource), true);
        getData.InsertSource(sourceAdd);
        onStop();
    }

    /*
    If someone wants to delete a Source he can use the Switch in the EditSource Fragment
    To Change the Settings in the DataBase you need this Method
     */
    private void deleteItem(SourceAdd changedSource) {
        getData.removeSource(changedSource);
    }

    /*
    Getter and setter Methods
     */
    private int getImageID(SourceAdd source) {
        return Objects.requireNonNull(uiElements.getPictureId(source.getName()));
    }

    /*
    Listener from the Adapter
    Shows Settings Changes and if one Source was Added or removed
     */
    @Override
    public void changedSource(SourceAdd changedSource) {
        if(source.getName().equals(Constants.ADDButton.name())){
            insertNewSource(changedSource);
            return;
        }
        boolean notification = changedSource.isNotification();
        changedSource.setNotification(!notification);
        getData.removeSource(changedSource);
        getData.InsertSource(changedSource);
        onStop();
    }
}