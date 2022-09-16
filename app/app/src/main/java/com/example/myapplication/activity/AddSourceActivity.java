package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;


import com.example.myapplication.R;
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.addSource.SourceAdd;
import com.example.myapplication.data.addSource.AddActivityIcons;
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
        DeleteSourceFragment.InputDeleteSourceFragment,
        EditSourceFragment.SettingsChanges {

    private final HashMap<Category,ArrayList<SourceAdd>> arrayListHashMap = new HashMap<>();
    private final HashMap<Category,ArrayList<SourceAdd>> selectedHashMap = new HashMap<>();
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


    // in this method all elements are initialized
    private void initUI() {
        setSupportActionBar(findViewById(R.id.toolbar_collapse));
        iniSelectedHashMap();
        initSource();
        initButton();
    }

    private void iniSelectedHashMap() {
        selectedHashMap.put(Category.Interests,data.getCategory(Category.Interests));
        selectedHashMap.put(Category.SocialMedia,data.getCategory(Category.SocialMedia));
        selectedHashMap.put(Category.Newspaper,data.getCategory(Category.Newspaper));
    }


    // This Method initialise the Buttons to close the Activity and show the Information Fragment
    private void initButton() {
        ImageButton buttonInformation = findViewById(R.id.addInfo);
        ImageButton backButton = findViewById(R.id.addback);
        buttonInformation.setOnClickListener(view -> {
            InformationFragment informationFragment = new InformationFragment();
            informationFragment.show(getSupportFragmentManager(),"");
        });
        backButton.setOnClickListener(view -> finish());
    }


    // in this method the recycler view is initialized and passed to the initRecyclerView method
    private void declareRecyclerView(){
        RecyclerView recyclerSocialMedia = findViewById(R.id.recyclerViewQuellenSM);
        RecyclerView recyclerNewsPaper = findViewById(R.id.recyclerViewQuellenNP);
        RecyclerView recyclerInterests = findViewById(R.id.recyclerViewQuellenIn);

        adapterNews = initRecyclerView(
                recyclerNewsPaper,
                selectedHashMap.get(Category.Newspaper));

        adapterInterests = initRecyclerView(
                recyclerInterests,
                selectedHashMap.get(Category.Interests));

        adapterSocialMedia = initRecyclerView(
                recyclerSocialMedia,
                selectedHashMap.get(Category.SocialMedia));

        recyclerInterests.setAdapter(adapterInterests);
        recyclerSocialMedia.setAdapter(adapterSocialMedia);
        recyclerNewsPaper.setAdapter(adapterNews);
    }

    /*
    This Method initialise the arrayListHashMap
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void initSource(){
        arrayListHashMap.put(Category.Interests, new ArrayList<>());
        arrayListHashMap.put(Category.SocialMedia,new ArrayList<>());
        arrayListHashMap.put(Category.Newspaper, new ArrayList<>());

        AddActivityIcons addActivityIcons = new AddActivityIcons();

        for(Category.interests categoryInterests: addActivityIcons.getInterestsHashMap().keySet()){
            Objects.requireNonNull(arrayListHashMap.get(Category.Interests)).
                    add(new SourceAdd(
                            categoryInterests.name(),
                            getDrawable(Objects.requireNonNull(
                                    addActivityIcons.getInterestsHashMap().get(categoryInterests))),
                            Category.Interests));
        }
        for(Category.news categoryNews: addActivityIcons.getNewsHashMap().keySet()){
            Objects.requireNonNull(arrayListHashMap.get(Category.Newspaper)).
                    add(new SourceAdd(
                            categoryNews.name(),
                            getDrawable(Objects.requireNonNull(
                                    addActivityIcons.getNewsHashMap().get(categoryNews))),
                            Category.Newspaper));
        }
        for(Category.socialMedia categorySocialMedia: addActivityIcons.getSocialMediaHashMap().keySet()){
            Objects.requireNonNull(arrayListHashMap.get(Category.SocialMedia)).
                    add(new SourceAdd(
                            categorySocialMedia.name(),
                            getDrawable(Objects.requireNonNull(
                                    addActivityIcons.getSocialMediaHashMap().get(categorySocialMedia))),
                            Category.SocialMedia));
        }
        updateSelectedHashMap();
    }
    //@TODO add image Path to DataBase to delete Loop
    @SuppressLint("UseCompatLoadingForDrawables")
    private void updateSelectedHashMap(){

        // TO add an ADD Button to each RecyclerView this three ADD Buttons will be
        // add to the ARRAYList.
        Objects.requireNonNull(selectedHashMap.get(Category.Newspaper))
                .add(new SourceAdd(Category.ADDButton.name(),
                        getDrawable(R.drawable.add),
                        Category.Newspaper));

        Objects.requireNonNull(selectedHashMap.get(Category.SocialMedia))
                .add(new SourceAdd(Category.ADDButton.name(),
                        getDrawable(R.drawable.add ),
                        Category.SocialMedia));

        Objects.requireNonNull(selectedHashMap.get(Category.Interests))
                .add(new SourceAdd(Category.ADDButton.name(),
                        getDrawable(R.drawable.add),
                        Category.Interests));
        declareRecyclerView();
    }


    // In this method, depending on a RecyclerView, the recycler view is
    // processed and connected to the adapter
    private AdapterListAddActivity initRecyclerView(
            RecyclerView recyclerView, ArrayList<SourceAdd> arrayList) {

        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        return new AdapterListAddActivity(this, this,arrayList);
    }


    //this method is inherited from the onclick listener here and
    // using it we can open the fragment depending on the button clicked
    @Override
    public void onItemClick(SourceAdd source) {
        if(!longSourceClick){
            EditSourceFragment editSourceFragment = new EditSourceFragment();
            editSourceFragment.setSource(source);
            editSourceFragment.setSettings(selectedHashMap.get(source.getCategories()));
            editSourceFragment.show(getSupportFragmentManager(),"");
            return;
        }
        if(source.getName().equals(Category.ADDButton.name())) return;
        DeleteSourceFragment dSf = new DeleteSourceFragment(this);
        dSf.setSource(source);
        dSf.show(getSupportFragmentManager(),"");
    }


    @Override
    public void inputDeleteSource(boolean result, SourceAdd source) {
        setAnimation(false);
        longSourceClick = false;
        if(result){
            data.removeIcon(source);
            Objects.requireNonNull(selectedHashMap.get(source.getCategories())).remove(source);
            if(source.getCategories()== Category.Interests){
                    adapterInterests.setSourceArrayList(Objects.requireNonNull(
                            selectedHashMap.get(source.getCategories())));
                    return;
                }
            }
            if(source.getCategories()== Category.SocialMedia){
                adapterSocialMedia.setSourceArrayList(Objects.requireNonNull(
                        selectedHashMap.get(source.getCategories())));
                return;
            }
            adapterNews.setSourceArrayList(Objects.requireNonNull(
                    selectedHashMap.get(source.getCategories())));
    }


    private void setAnimation(boolean boo){
        for(Category categories:selectedHashMap.keySet()){
            for(SourceAdd source: Objects.requireNonNull(selectedHashMap.get(categories))){
                source.setSetAnimation(boo);
            }
            selectedHashMap.put(categories,selectedHashMap.get(categories));
        }
        adapterSocialMedia.setSourceArrayList(Objects.requireNonNull(
                selectedHashMap.get(Category.SocialMedia)));

        adapterInterests.setSourceArrayList(Objects.requireNonNull(
                selectedHashMap.get(Category.Interests)));

        adapterNews.setSourceArrayList(Objects.requireNonNull(
                selectedHashMap.get(Category.Newspaper)));
    }


    @Override
    public void onLongClick(SourceAdd source) {
        if(!longSourceClick){
            longSourceClick = true;
            setAnimation(true);
            return;
        }
        longSourceClick = false;
        setAnimation(false);
    }

    
    @Override
    public void getChangedSourceArrayList(ArrayList<SourceAdd> sourceArrayList, Category c) {
        selectedHashMap.put(c, sourceArrayList);
    }
}