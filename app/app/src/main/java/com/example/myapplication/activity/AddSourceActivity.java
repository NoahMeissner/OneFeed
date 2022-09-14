package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageButton;


import com.example.myapplication.R;
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.addSource.SourceAdd;
import com.example.myapplication.database.AddActivityIcons;
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
    private AdapterListAddActivity adapterNews;
    private AdapterListAddActivity adapterSocialMedia;
    private AdapterListAddActivity adapterInterests;
    private boolean longSourceClick = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
        initUI();
    }


    // in this method all elements are initialized
    private void initUI() {
        setSupportActionBar(findViewById(R.id.toolbar_collapse));
        initSource();
        declareRecyclerView();
        initButton();
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
                arrayListHashMap.get(Category.Newspaper));

        adapterInterests = initRecyclerView(
                recyclerInterests,
                arrayListHashMap.get(Category.Interests));

        adapterSocialMedia = initRecyclerView(
                recyclerSocialMedia,
                arrayListHashMap.get(Category.SocialMedia));

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
                            getDrawable(addActivityIcons.getInterestsHashMap().get(categoryInterests)),
                            Category.Interests));
        }
        for(Category.news categoryNews: addActivityIcons.getNewsHashMap().keySet()){
            Objects.requireNonNull(arrayListHashMap.get(Category.Newspaper)).
                    add(new SourceAdd(
                            categoryNews.name(),
                            getDrawable(addActivityIcons.getNewsHashMap().get(categoryNews)),
                            Category.Newspaper));
        }
        for(Category.socialMedia categorySocialMedia: addActivityIcons.getSocialMediaHashMap().keySet()){
            Objects.requireNonNull(arrayListHashMap.get(Category.SocialMedia)).
                    add(new SourceAdd(
                            categorySocialMedia.name(),
                            getDrawable(addActivityIcons.getSocialMediaHashMap().get(categorySocialMedia)),
                            Category.Interests));
        }

        // TO add an ADD Button to each RecyclerView this three ADD Buttons will be
        // add to the ARRAYList.
        Objects.requireNonNull(arrayListHashMap.get(Category.Newspaper))
                .add(new SourceAdd(Category.ADDButton.name(),
                        getDrawable(R.drawable.add),
                        Category.Newspaper));

        Objects.requireNonNull(arrayListHashMap.get(Category.SocialMedia))
                .add(new SourceAdd(Category.ADDButton.name(),
                        getDrawable(R.drawable.add ),
                        Category.SocialMedia));

        Objects.requireNonNull(arrayListHashMap.get(Category.Interests))
                .add(new SourceAdd(Category.ADDButton.name(),
                        getDrawable(R.drawable.add),
                        Category.Interests));
    }


    // In this method, depending on a RecyclerView, the recycler view is processed and connected to the adapter
    private AdapterListAddActivity initRecyclerView(RecyclerView recyclerView, ArrayList<SourceAdd> arrayList) {
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
            editSourceFragment.setSettings(arrayListHashMap.get(source.getCategories()));
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
            Objects.requireNonNull(arrayListHashMap.get(source.getCategories())).remove(source);
            if(source.getCategories()== Category.Interests){
                adapterInterests.setSourceArrayList(arrayListHashMap.get(source.getCategories()));
                return;
            }
            if(source.getCategories()== Category.SocialMedia){
                adapterSocialMedia.setSourceArrayList(arrayListHashMap.get(source.getCategories()));
                return;
            }
            adapterNews.setSourceArrayList(arrayListHashMap.get(source.getCategories()));
        }
    }


    private void setAnimation(boolean boo){
        for(Category categories: arrayListHashMap.keySet()){
            for(SourceAdd source: Objects.requireNonNull(arrayListHashMap.get(categories))){
                source.setSetAnimation(boo);
            }
            arrayListHashMap.put(categories,arrayListHashMap.get(categories));
        }
        adapterSocialMedia.setSourceArrayList(arrayListHashMap.get(Category.SocialMedia));
        adapterInterests.setSourceArrayList(arrayListHashMap.get(Category.Interests));
        adapterNews.setSourceArrayList(arrayListHashMap.get(Category.Newspaper));
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
        arrayListHashMap.put(c, sourceArrayList);
    }
}