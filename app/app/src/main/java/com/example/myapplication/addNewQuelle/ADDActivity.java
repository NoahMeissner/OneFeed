package com.example.myapplication.addNewQuelle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageButton;


import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.Fragement.DeleteSourceFragment;
import com.example.myapplication.addNewQuelle.Fragement.EditSourceFragment;
import com.example.myapplication.addNewQuelle.Adapter.AdapterListAddActivity;
import com.example.myapplication.addNewQuelle.Fragement.InformationFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ADDActivity extends AppCompatActivity implements
        AdapterListAddActivity.OnItemClickListener,
        AdapterListAddActivity.longItemClickListener,
        DeleteSourceFragment.InputDeleteSourceFragment,
        EditSourceFragment.SettingsChanges {

    private final HashMap<String[],Drawable> hashMap = new HashMap<>();
    private final HashMap<Categories,ArrayList<SourceAdd>> arrayListHashMap = new HashMap<>();
    private AdapterListAddActivity adapterNews;
    private AdapterListAddActivity adapterSocialMedia;
    private AdapterListAddActivity adapterInterests;
    private boolean click = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
        initUI();
    }


    // in this method all elements are initialized
    private void initUI() {
        setSupportActionBar(findViewById(R.id.toolbar_collapse));
        initHashMap();
        editPictures();
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
        RecyclerView recylerViewSM = findViewById(R.id.recyclerViewQuellenSM);
        RecyclerView recylerViewNP = findViewById(R.id.recyclerViewQuellenNP);
        RecyclerView recyclerViewIn = findViewById(R.id.recyclerViewQuellenIn);

        adapterNews = initRecyclerView(
                recylerViewNP,
                arrayListHashMap.get(Categories.Newspaper));

        adapterInterests = initRecyclerView(
                recyclerViewIn,
                arrayListHashMap.get(Categories.Interests));

        adapterSocialMedia = initRecyclerView(
                recylerViewSM,
                arrayListHashMap.get(Categories.SocialMedia));

        recyclerViewIn.setAdapter(adapterInterests);
        recylerViewSM.setAdapter(adapterSocialMedia);
        recylerViewNP.setAdapter(adapterNews);
    }


    //In this method, the hashmap is initialized in which the category and the image
    // are located in order to assign them quickly
    @SuppressLint("UseCompatLoadingForDrawables")
    private void initHashMap(){
        hashMap.put( new String[]{
                        Categories.SocialMedia.name(),
                        Categories.socialMedia.Twitter.name()},
                getDrawable(R.drawable.twitter_icon)
        );

        hashMap.put(new String[]{Categories.SocialMedia.name(),
                        Categories.socialMedia.Reddit.name()},
                        getDrawable(R.drawable.reddit));

        hashMap.put(new String[]{Categories.Newspaper.name(),
                Categories.news.FAZ.name()},
                getDrawable(R.drawable.faz));

        hashMap.put(new String[]{Categories.Newspaper.name(),
                Categories.news.Spiegel.name()},
                getDrawable(R.drawable.spiegel));

        hashMap.put(new String[]{Categories.Interests.name(),
                Categories.interests.Politik.name()},
                getDrawable(R.drawable.world));

        hashMap.put(new String[]{Categories.Interests.name(),
                Categories.interests.Wirtschaft.name()},
                getDrawable(R.drawable.business));

        hashMap.put(new String[]{Categories.Interests.name(),
                Categories.interests.Corona.name()},
                getDrawable(R.drawable.coronavirus));

        hashMap.put(new String[]{Categories.Interests.name(),
                Categories.interests.Technik.name()},
                getDrawable(R.drawable.tech));

        hashMap.put(new String[]{Categories.Interests.name(),
                Categories.interests.Gaming.name()},
                getDrawable(R.drawable.sports));

        hashMap.put(new String[]{Categories.Interests.name(),
                Categories.interests.Sport.name()},
                getDrawable(R.drawable.sport));
    }


    // With this method you can easily edit the images of the buttons
    @SuppressLint("ResourceAsColor")
    private void editPictures(){
        // Todo: simplify attr resolution?
        TypedArray a = getTheme().obtainStyledAttributes(
                R.style.AppTheme, new int[] {androidx.appcompat.R.attr.colorPrimary}
        );
        int attributeResourceId = a.getResourceId(0, 0);
        for(String[] s: hashMap.keySet()){
            if(!Objects.equals(s[0], String.valueOf(Categories.Newspaper))){
                Objects.requireNonNull(hashMap.get(s)).setTint(getColor(attributeResourceId));
            }
        }
    }


    //in this method the source objects are created
    @SuppressLint("UseCompatLoadingForDrawables")
    private void initSource(){
        arrayListHashMap.put(Categories.Interests,new ArrayList<>());
        arrayListHashMap.put(Categories.SocialMedia,new ArrayList<>());
        arrayListHashMap.put(Categories.Newspaper,new ArrayList<>());
        for(String[]s : hashMap.keySet()){
            if(s[0].equals(String.valueOf(Categories.SocialMedia))){
                Objects.requireNonNull(arrayListHashMap.get(Categories.SocialMedia))
                        .add(new SourceAdd(s[1],hashMap.get(s),Categories.SocialMedia));
            }
            if(s[0].equals(String.valueOf(Categories.Newspaper))){
                Objects.requireNonNull(arrayListHashMap.get(Categories.Newspaper))
                        .add(new SourceAdd(s[1],hashMap.get(s),Categories.Newspaper));
            }
            if(s[0].equals(String.valueOf(Categories.Interests))){
                Objects.requireNonNull(arrayListHashMap.get(Categories.Interests))
                        .add(new SourceAdd(s[1],hashMap.get(s),Categories.Interests));
            }
        }
        Objects.requireNonNull(arrayListHashMap.get(Categories.Newspaper))
                .add(new SourceAdd(Categories.ADDButton.name(),
                        getDrawable(R.drawable.add),
                        Categories.Newspaper));

        Objects.requireNonNull(arrayListHashMap.get(Categories.SocialMedia))
                .add(new SourceAdd(Categories.ADDButton.name(),
                        getDrawable(R.drawable.add ),
                        Categories.SocialMedia));

        Objects.requireNonNull(arrayListHashMap.get(Categories.Interests))
                .add(new SourceAdd(Categories.ADDButton.name(),
                        getDrawable(R.drawable.add),
                        Categories.Interests));
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
        if(!click){
            EditSourceFragment edf = new EditSourceFragment();
            edf.setSource(source);
            edf.setSettings(arrayListHashMap.get(source.getCategories()));
            edf.show(getSupportFragmentManager(),"");
            return;
        }
        if(source.getName().equals(Categories.ADDButton.name())) return;
        DeleteSourceFragment dSf = new DeleteSourceFragment(this);
        dSf.setSource(source);
        dSf.show(getSupportFragmentManager(),"");
    }


    @Override
    public void inputDeleteSource(boolean result, SourceAdd source) {
        setAnimation(false);
        click = false;
        if(result){
            Objects.requireNonNull(arrayListHashMap.get(source.getCategories())).remove(source);
            if(source.getCategories()== Categories.Interests){
                adapterInterests.setSourceArrayList(arrayListHashMap.get(source.getCategories()));
                return;
            }
            if(source.getCategories()== Categories.SocialMedia){
                adapterSocialMedia.setSourceArrayList(arrayListHashMap.get(source.getCategories()));
                return;
            }
            adapterNews.setSourceArrayList(arrayListHashMap.get(source.getCategories()));
        }
    }


    private void setAnimation(boolean boo){
        for(Categories categories: arrayListHashMap.keySet()){
            for(SourceAdd source: Objects.requireNonNull(arrayListHashMap.get(categories))){
                source.setSetAnimation(boo);
            }
            arrayListHashMap.put(categories,arrayListHashMap.get(categories));
        }
        adapterSocialMedia.setSourceArrayList(arrayListHashMap.get(Categories.SocialMedia));
        adapterInterests.setSourceArrayList(arrayListHashMap.get(Categories.Interests));
        adapterNews.setSourceArrayList(arrayListHashMap.get(Categories.Newspaper));
    }


    @Override
    public void onLongClick(SourceAdd source) {
        if(!click){
            click = true;
            setAnimation(true);
            return;
        }
        click = false;
        setAnimation(false);
    }

    
    @Override
    public void getChangedSourceArrayList(ArrayList<SourceAdd> sourceArrayList, Categories c) {
        arrayListHashMap.put(c, sourceArrayList);
    }
}