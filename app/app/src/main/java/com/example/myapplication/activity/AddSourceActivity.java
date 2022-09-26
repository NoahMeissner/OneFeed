package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;


import com.example.myapplication.R;
import com.example.myapplication.animation.addSource.OnSwipeTouchListener;
import com.example.myapplication.data.addSource.Constants;
import com.example.myapplication.data.addSource.SourceAdd;
import com.example.myapplication.database.GetData;
import com.example.myapplication.fragment.addSource.DeleteSourceFragment;
import com.example.myapplication.fragment.addSource.EditSourceFragment;
import com.example.myapplication.adapter.AdapterListAddActivity;
import com.example.myapplication.fragment.addSource.InformationFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AddSourceActivity extends AppCompatActivity implements
        AdapterListAddActivity.OnItemClickListener,
        AdapterListAddActivity.longItemClickListener,
        DeleteSourceFragment.DeleteSourceFragmentInterface,
        EditSourceFragment.EditSourceFragmentChanges {

    /*
    Constants
     */
    private final HashMap<Constants,ArrayList<SourceAdd>> enabledSourcesHashMap = new HashMap<>();
    private AdapterListAddActivity adapterNews;
    private AdapterListAddActivity adapterSocialMedia;
    private AdapterListAddActivity adapterInterests;
    private boolean longSourceClick = false;
    private GetData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        data = new GetData(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
        initUI();
    }

    /*
    in this method all elements are initialized
     */
    private void initUI() {
        setSupportActionBar(findViewById(R.id.toolbar_collapse));
        initHashMap();
        addAddButtonToSelectedHashMap();
        initButton();
        initGestures();
    }

    //@TODO zum Laufen Bringen
    private void initGestures() {
        /*
        This Method will initial the Swipe Gestures
         */
        View relativeLayout = findViewById(R.id.app_barcollapse);
        OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(
                relativeLayout, swipe -> Log.d("Gesture", swipe.name()));
        relativeLayout.setOnTouchListener(onSwipeTouchListener);
    }

    /*
    This Method initialized the HashMap
     */
    private void initHashMap() {
        enabledSourcesHashMap.put(Constants.Interests,data.getCategory(Constants.Interests));
        enabledSourcesHashMap.put(Constants.SocialMedia,data.getCategory(Constants.SocialMedia));
        enabledSourcesHashMap.put(Constants.Newspaper,data.getCategory(Constants.Newspaper));
    }

    /*
     This Method initialise the Buttons to close the Activity and show the Information Fragment
     */
    private void initButton() {
        ImageButton buttonInformation = findViewById(R.id.addInfo);
        ImageButton backButton = findViewById(R.id.addback);
        buttonInformation.setOnClickListener(view -> {
            InformationFragment informationFragment = new InformationFragment();
            informationFragment.show(getSupportFragmentManager(),"");
        });
        backButton.setOnClickListener(view -> closeActivity());
    }

    private void closeActivity(){
        Intent intent = new Intent(getBaseContext(), FeedActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }

    /*
    in this method the recycler view is initialized and passed to the initRecyclerView method
     */
    private void declareRecyclerView(){
        RecyclerView recyclerSocialMedia = findViewById(R.id.recyclerViewQuellenSM);
        RecyclerView recyclerNewsPaper = findViewById(R.id.recyclerViewQuellenNP);
        RecyclerView recyclerInterests = findViewById(R.id.recyclerViewQuellenIn);

        adapterNews = initRecyclerView(
                recyclerNewsPaper,
                enabledSourcesHashMap.get(Constants.Newspaper));

        adapterInterests = initRecyclerView(
                recyclerInterests,
                enabledSourcesHashMap.get(Constants.Interests));

        adapterSocialMedia = initRecyclerView(
                recyclerSocialMedia,
                enabledSourcesHashMap.get(Constants.SocialMedia));

        recyclerInterests.setAdapter(adapterInterests);
        recyclerSocialMedia.setAdapter(adapterSocialMedia);
        recyclerNewsPaper.setAdapter(adapterNews);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void addAddButtonToSelectedHashMap(){
        /*
        TO add an ADD Button to each RecyclerView this three ADD Buttons will be
        add to the ARRAYList.
        */
        Objects.requireNonNull(enabledSourcesHashMap.get(Constants.Newspaper))
                .add(new SourceAdd(Constants.ADDButton.name(),
                        getDrawable(R.drawable.add),
                        Constants.Newspaper));

        Objects.requireNonNull(enabledSourcesHashMap.get(Constants.SocialMedia))
                .add(new SourceAdd(Constants.ADDButton.name(),
                        getDrawable(R.drawable.add ),
                        Constants.SocialMedia));

        Objects.requireNonNull(enabledSourcesHashMap.get(Constants.Interests))
                .add(new SourceAdd(Constants.ADDButton.name(),
                        getDrawable(R.drawable.add),
                        Constants.Interests));
        declareRecyclerView();
    }



    /*
    In this method, depending on a RecyclerView, the recycler view is
    processed and connected to the adapter
    */
    private AdapterListAddActivity initRecyclerView(
            RecyclerView recyclerView, ArrayList<SourceAdd> arrayList) {

        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        return new AdapterListAddActivity(this, this,arrayList);
    }

    /*
    This Method will set the Animation if one Item was Long Pressed
     */
    private void setAnimation(boolean boo){
        for(Constants categories: enabledSourcesHashMap.keySet()){
            for(SourceAdd source: Objects.requireNonNull(enabledSourcesHashMap.get(categories))){
                source.setSetAnimation(boo);
            }
            enabledSourcesHashMap.put(categories, enabledSourcesHashMap.get(categories));
        }
        adapterSocialMedia.setSourceArrayList(Objects.requireNonNull(
                enabledSourcesHashMap.get(Constants.SocialMedia)));

        adapterInterests.setSourceArrayList(Objects.requireNonNull(
                enabledSourcesHashMap.get(Constants.Interests)));

        adapterNews.setSourceArrayList(Objects.requireNonNull(
                enabledSourcesHashMap.get(Constants.Newspaper)));
    }

    /*
    This Method initialize the EditSourceFragment
     */
    private void initEditSourceFragment(SourceAdd source){
        EditSourceFragment editSourceFragment = new EditSourceFragment(
                source,
                enabledSourcesHashMap.get(source.getCategories()),
                this,
                data);

        editSourceFragment.setDataChanged(this);
        editSourceFragment.show(getSupportFragmentManager(),"");
    }


    //this method is inherited from the onclick listener here and
    // using it we can open the fragment depending on the button clicked
    @Override
    public void onItemClick(SourceAdd source) {
        /*
        this if condition checks if there was set a long Click to differentiate between Delete
         an Item and change the Settings of one
         */
        if(!longSourceClick){
            initEditSourceFragment(source);
            return;
        }
        if(source.getName().equals(Constants.ADDButton.name())) return;
        /*
        If the User clicked on the Item and the pressed long before the Delete Source Fragment will
        be initialised
         */
        DeleteSourceFragment dSf = new DeleteSourceFragment(this,source);
        dSf.show(getSupportFragmentManager(),"");
    }

    /*
    This Method recognizes from the DeleteSourceFragment if the User Deleted one Item
    */
    @Override
    public void deleteSourceFromLongClick(boolean result, SourceAdd source) {
        /*
        set Constants falls to show the User the Process has end
         */
        longSourceClick = false;
        setAnimation(false);
        if (result) {
            /*
            If he pressed yes the Item will be deleted. To show him the difference the Icon will be
            deleted in the Adapter Array List too
             */
            data.removeSource(source);
            refresh();
        }
    }

    /*
    This Method recognize, if the User press long on one Item
     */
    @Override
    public void onLongClick(SourceAdd source) {
        /*
        This If Clause checks if he pressed the item long before,
        Important to stop the Animation and the Delete Progress
         */
        if(!longSourceClick){
            longSourceClick = true;
            setAnimation(true);
            return;
        }
        longSourceClick = false;
        setAnimation(false);
    }

    /*
    This Method will refresh the Activity
     */
    private void refresh(){
        //@TODO Animation beenden
        Intent refresh = new Intent(this, AddSourceActivity.class);
        overridePendingTransition(0, 0);
        startActivity(refresh);
        overridePendingTransition(0, 0);
        this.finish();
    }


    @Override
    public void dataHasChanged(Boolean b, SourceAdd sourceAdd) {
        if(b == null){
            return;
        }
        /*
        Update Selected HashMap from the DataBase to update the Recycler viewer
         */
        refresh();
    }
}