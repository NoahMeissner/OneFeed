package com.example.myapplication.addNewQuelle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageButton;

import com.example.myapplication.FeedActivity;
import com.example.myapplication.InitialProcess.Activities.InitialActivity;
import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.Fragement.DeleteSourceFragement;
import com.example.myapplication.addNewQuelle.Fragement.EditQuellenFragement;
import com.example.myapplication.addNewQuelle.Fragement.InformationenFragement;
import com.example.myapplication.addNewQuelle.Adapter.AdapterListAddActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ADDActivity extends AppCompatActivity implements AdapterListAddActivity.OnItemClickListener, AdapterListAddActivity.longItemClickListener, DeleteSourceFragement.InputDeleteSourceFragement,EditQuellenFragement.SettingsChanges {

    private final HashMap<String[],Drawable> hashMap = new HashMap<>();
    private HashMap<Categories,ArrayList<Quellen>> arrayListHashMap = new HashMap<>();
    private AdapterListAddActivity adapterNews;
    private AdapterListAddActivity adapterSocialMedia;
    private AdapterListAddActivity adapterInteressen;
    private boolean click = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    // in this method all elements are initialized
    private void initUI() {
        setContentView(R.layout.activity_addactivity);
        initHashMap();
        editpictures();
        initQuellen();
        declareRecyclerView();
        initButton();
    }

    // This Method initialise the Buttons to close the Actitivity and show the Information Fragement
    private void initButton() {
        ImageButton buttonInformation = findViewById(R.id.informationButton);
        ImageButton backButton = findViewById(R.id.imagebackButton);
        buttonInformation.setOnClickListener(view -> {
            InformationenFragement informationenFragement = new InformationenFragement();
            informationenFragement.show(getSupportFragmentManager(),"openInformationFragement");
        });
        backButton.setOnClickListener(view -> {
            //@TODO zurück zum Home FEED
            Intent intent = new Intent(this, FeedActivity.class);
            startActivity(intent);
        });
    }

    // in this method the recycler view is initialized and passed to the initRecyclerView method
    private void declareRecyclerView(){
        RecyclerView recylerViewSM = findViewById(R.id.recyclerViewQuellenSM);
        RecyclerView recylerViewNP = findViewById(R.id.recyclerViewQuellenNP);
        RecyclerView recyclerViewIn = findViewById(R.id.recyclerViewQuellenIn);
        adapterNews = initRecyclerView(recylerViewNP,arrayListHashMap.get(Categories.Newspaper));
        adapterInteressen = initRecyclerView(recyclerViewIn,arrayListHashMap.get(Categories.Interessen));
        adapterSocialMedia = initRecyclerView(recylerViewSM,arrayListHashMap.get(Categories.SocialMedia));
        recyclerViewIn.setAdapter(adapterInteressen);
        recylerViewSM.setAdapter(adapterSocialMedia);
        recylerViewNP.setAdapter(adapterNews);
    }

    //In this method, the hashmap is initialized in which the category and the image are located in order to assign them quickly
    @SuppressLint("UseCompatLoadingForDrawables")
    private void initHashMap(){
        hashMap.put(new String[]{Categories.SocialMedia.name(), Categories.socialMedia.Twitter.name()},getDrawable(R.drawable.twitter_icon));
        hashMap.put(new String[]{Categories.SocialMedia.name(), Categories.socialMedia.Reddit.name()},getDrawable(R.drawable.reddit));
        hashMap.put(new String[]{Categories.Newspaper.name(), Categories.news.FAZ.name()},getDrawable(R.drawable.faz));
        hashMap.put(new String[]{Categories.Newspaper.name(), Categories.news.Spiegel.name()},getDrawable(R.drawable.spiegel));
        hashMap.put(new String[]{Categories.Interessen.name(), Categories.interests.Politik.name()},getDrawable(R.drawable.world));
        hashMap.put(new String[]{Categories.Interessen.name(), Categories.interests.Wirtschaft.name()},getDrawable(R.drawable.business));
        hashMap.put(new String[]{Categories.Interessen.name(), Categories.interests.Corona.name()},getDrawable(R.drawable.coronavirus));
        hashMap.put(new String[]{Categories.Interessen.name(), Categories.interests.Technik.name()},getDrawable(R.drawable.tech));
        hashMap.put(new String[]{Categories.Interessen.name(), Categories.interests.Gaming.name()},getDrawable(R.drawable.sports));
        hashMap.put(new String[]{Categories.Interessen.name(), Categories.interests.Sport.name()},getDrawable(R.drawable.sport));
    }

    // With this method you can easily edit the images of the buttons
    @SuppressLint("ResourceAsColor")
    private void editpictures(){
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
    private void initQuellen(){
        arrayListHashMap.put(Categories.Interessen,new ArrayList<>());
        arrayListHashMap.put(Categories.SocialMedia,new ArrayList<>());
        arrayListHashMap.put(Categories.Newspaper,new ArrayList<>());
        for(String[]s : hashMap.keySet()){
            if(s[0].equals(String.valueOf(Categories.SocialMedia))){
                arrayListHashMap.get(Categories.SocialMedia).add(new Quellen(s[1],hashMap.get(s),Categories.SocialMedia));
            }
            if(s[0].equals(String.valueOf(Categories.Newspaper))){
                arrayListHashMap.get(Categories.Newspaper).add(new Quellen(s[1],hashMap.get(s),Categories.Newspaper));
            }
            if(s[0].equals(String.valueOf(Categories.Interessen))){
                arrayListHashMap.get(Categories.Interessen).add(new Quellen(s[1],hashMap.get(s),Categories.Interessen));
            }
        }
        arrayListHashMap.get(Categories.Newspaper).add(new Quellen(Categories.ADDButton.name(), getDrawable(R.drawable.add),Categories.Newspaper));
        arrayListHashMap.get(Categories.SocialMedia).add(new Quellen(Categories.ADDButton.name(),getDrawable(R.drawable.add ),Categories.SocialMedia));
        arrayListHashMap.get(Categories.Interessen).add(new Quellen(Categories.ADDButton.name(), getDrawable(R.drawable.add),Categories.Interessen));
    }

    // In this method, depending on a RecyclerView, the recycler view is processed and connected to the adapter
    private AdapterListAddActivity initRecyclerView(RecyclerView recyclerView, ArrayList<Quellen> arrayList) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        AdapterListAddActivity recyclerViewAdapter = new AdapterListAddActivity(this,this,arrayList);
        return  recyclerViewAdapter;
    }

    //this method is inherited from the onclick listener here and using it we can open the fragment depending on the button clicked
    @Override
    public void onItemClick(Quellen quellen) {
        if(!click){
            EditQuellenFragement editQuellenFragement = new EditQuellenFragement(this);
            editQuellenFragement.setQuellen(quellen);
            editQuellenFragement.setSettings(arrayListHashMap.get(quellen.getCategories()));
            editQuellenFragement.show(getSupportFragmentManager(),"");
            return;
        }
        if(quellen.getName().equals(Categories.ADDButton.name())) return;
        DeleteSourceFragement deleteSourceFragement = new DeleteSourceFragement(this);
        deleteSourceFragement.setQuellen(quellen);
        deleteSourceFragement.show(getSupportFragmentManager(),"");
    }

    @Override
    public void inputDeleteSource(boolean result, Quellen quellen) {
        setAnimation(false);
        click = false;
        if(result){
            Objects.requireNonNull(arrayListHashMap.get(quellen.getCategories())).remove(quellen);
            if(quellen.getCategories()== Categories.Interessen){
                adapterInteressen.setQuellenArrayList(arrayListHashMap.get(quellen.getCategories()));
                return;
            }
            if(quellen.getCategories()== Categories.SocialMedia){
                adapterSocialMedia.setQuellenArrayList(arrayListHashMap.get(quellen.getCategories()));
                return;
            }
            adapterNews.setQuellenArrayList(arrayListHashMap.get(quellen.getCategories()));
        }
    }

    private void setAnimation(boolean boo){
        for(Categories categories: arrayListHashMap.keySet()){
            for(Quellen quellen: Objects.requireNonNull(arrayListHashMap.get(categories))){
                quellen.setSetAnimation(boo);
            }
            arrayListHashMap.put(categories,arrayListHashMap.get(categories));
        }
        adapterSocialMedia.setQuellenArrayList(arrayListHashMap.get(Categories.SocialMedia));
        adapterInteressen.setQuellenArrayList(arrayListHashMap.get(Categories.Interessen));
        adapterNews.setQuellenArrayList(arrayListHashMap.get(Categories.Newspaper));
    }

    @Override
    public void onLongClick(Quellen quellen) {
        if(!click){
            click = true;
            setAnimation(true);
            return;
        }
        click = false;
        setAnimation(false);
        return;
    }

    @Override
    public void getChangedQuellenArrayList(ArrayList<Quellen> quellenArrayList,Categories categories) {

    }
}