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
import com.example.myapplication.addNewQuelle.adapter.AdapterListAddActivity;
import com.google.android.material.color.MaterialColors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ADDActivity extends AppCompatActivity implements AdapterListAddActivity.OnItemClickListener, AdapterListAddActivity.longItemClickListener {


    private final ArrayList<Quellen> socialMediaQuellenArrayList = new ArrayList<>();
    private final ArrayList<Quellen> newsArrayList = new ArrayList<>();
    private final ArrayList<Quellen> interestsArrayList = new ArrayList<>();
    private final HashMap<String[],Drawable> hashMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
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
        initRecyclerView(recylerViewNP,newsArrayList);
        initRecyclerView(recylerViewSM,socialMediaQuellenArrayList);
        initRecyclerView(recyclerViewIn,interestsArrayList);
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
        for(String[]s : hashMap.keySet()){
            if(s[0].equals(String.valueOf(Categories.SocialMedia))){
                socialMediaQuellenArrayList.add(new Quellen(s[1],hashMap.get(s),Categories.SocialMedia));
            }
            if(s[0].equals(String.valueOf(Categories.Newspaper))){
                newsArrayList.add(new Quellen(s[1],hashMap.get(s),Categories.Newspaper));
            }
            if(s[0].equals(String.valueOf(Categories.Interessen))){
                interestsArrayList.add(new Quellen(s[1],hashMap.get(s),Categories.Interessen));
            }

        }
            socialMediaQuellenArrayList.add(new Quellen(Categories.ADDButton.name(),getDrawable(R.drawable.add),Categories.SocialMedia));
            newsArrayList.add(new Quellen(Categories.ADDButton.name(), getDrawable(R.drawable.add),Categories.Newspaper));
            interestsArrayList.add(new Quellen(Categories.ADDButton.name(), getDrawable(R.drawable.add),Categories.Interessen));
    }

    // In this method, depending on a RecyclerView, the recycler view is processed and connected to the adapter
    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Quellen> arrayList) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        AdapterListAddActivity recyclerViewAdapter = new AdapterListAddActivity(this,this,arrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    //this method is inherited from the onclick listener here and using it we can open the fragment depending on the button clicked
    @Override
    public void onItemClick(Quellen quellen) {

        EditQuellenFragement editQuellenFragement = new EditQuellenFragement();
        editQuellenFragement.setName(quellen.getName());
        editQuellenFragement.setCategory(String.valueOf(quellen.getCategories()));
        editQuellenFragement.setDrawable(quellen.getImage());
        editQuellenFragement.show(getSupportFragmentManager(),"My Fragement");
    }

    @Override
    public void onLongClick(Quellen quellen) {
        //@TODO LongClick funktioniert est muss nur alle betreffen und Farbe ändern und Fragement für löschen
        DeleteSourceFragement deleteSourceFragement = new DeleteSourceFragement();
        deleteSourceFragement.setImage(quellen.getImage());
        deleteSourceFragement.setHeadline(quellen.getName());
        deleteSourceFragement.show(getSupportFragmentManager(),"deleteFragement");
    }
}