package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import com.example.myapplication.R;
import com.example.myapplication.animation.addSource.OnSwipeTouchListener;
import com.example.myapplication.animation.addSource.Swipe;
import com.example.myapplication.data.addSource.Constants;
import com.example.myapplication.data.addSource.SourceAdd;
import com.example.myapplication.database.GetData;
import com.example.myapplication.fragment.addSource.DeleteSourceFragment;
import com.example.myapplication.fragment.addSource.EditSourceFragment;
import com.example.myapplication.adapter.AdapterListAddActivity;
import com.example.myapplication.fragment.addSource.InformationFragment;
import com.example.myapplication.notification.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class AddSourceActivity extends AppCompatActivity implements
        AdapterListAddActivity.OnItemClickListener,
        AdapterListAddActivity.longItemClickListener,
        DeleteSourceFragment.DeleteSourceFragmentInterface{

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
        startService();
    }

    private void initGestures() {
        /*
        This Method will initial the Swipe Gestures
         */
        View appBar = findViewById(R.id.component_app_bar_id);
        View activity = findViewById(R.id.addActivityView);
        //@TODO repair
        setSwipeListener(appBar);
        setSwipeListener(activity);
    }

    private void setSwipeListener(View view){
        OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(
                view, swipe -> {
            if (swipe== Swipe.Right){
                closeActivity();
            }
        });
        onSwipeTouchListener.setGestureListener();
    }

    /*
    This Method initialized the HashMap
     */
    private void initHashMap() {
        data.getSourceAdds().observe(this, l -> {
            enabledSourcesHashMap.clear();
            enabledSourcesHashMap.put(
                    Constants.Interests,
                    data.getCategory(Constants.Interests, l));

            enabledSourcesHashMap.put(
                    Constants.SocialMedia,
                    data.getCategory(Constants.SocialMedia, l));

            enabledSourcesHashMap.put(
                    Constants.Newspaper,
                    data.getCategory(Constants.Newspaper, l));

            addAddButtonToSelectedHashMap();
            setPreferences(l);
        });
    }

    /*
    This Method will get the Room Data Sources List and safes in shared Preferences the source Names
     */
    private void setPreferences(List<SourceAdd> sources) {
        SharedPreferences preferences = getSharedPreferences(
                getBaseContext()
                        .getResources()
                        .getString(R.string.initProcesBoolean), 0);

        SharedPreferences.Editor editPreferences = preferences.edit();

        editPreferences.putStringSet(
                Constants.initial.NotificationList.name(),
                checkNotification(sources));

        editPreferences.apply();
    }

    /*
    This Method will start the Service
     */
    private void startService() {
        Intent intent = new Intent(this, Service.class);
        if(isMyServiceRunning()){
            getBaseContext().stopService(intent);
        }
        startService(intent);
    }

    /*
    This Method safes the names from the Source Adds in one list
        Sources which declared no Notification will be deleted in this List
     */
    private HashSet<String> checkNotification(List<SourceAdd> sourceAdds) {
        HashSet<String> list = new HashSet<>();
        for(SourceAdd sources:sourceAdds){
            if (sources.isNotification()){
                list.add(sources.getName());
            }
        }
        return list;
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
        // Initial RecyclerViewer
        RecyclerView recyclerSocialMedia = findViewById(R.id.recyclerViewQuellenSM);
        RecyclerView recyclerNewsPaper = findViewById(R.id.recyclerViewQuellenNP);
        RecyclerView recyclerInterests = findViewById(R.id.recyclerViewQuellenIn);
        // edit recycler Viewer that they are not scrollable
        recyclerInterests.setNestedScrollingEnabled(false);
        recyclerNewsPaper.setNestedScrollingEnabled(false);
        recyclerSocialMedia.setNestedScrollingEnabled(false);

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
        try {
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
        } catch (NullPointerException exception) {
            return;
        }
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
                data);
        editSourceFragment.show(getSupportFragmentManager(),"");
    }

    // This Method check if the Service is running
    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo
                service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (Service.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
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
}