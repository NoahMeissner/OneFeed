package com.example.myapplication.addNewQuelle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.adapter.AdapterListAddActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ADDActivity extends AppCompatActivity implements AdapterListAddActivity.OnItemClickListener {


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
        hashMap.put(new String[]{String.valueOf(Categories.SocialMedia), "Twitter"},getDrawable(R.drawable.twitter_icon));
        hashMap.put(new String[]{String.valueOf(Categories.SocialMedia), "Reddit"},getDrawable(R.drawable.reddit));
        hashMap.put(new String[]{String.valueOf(Categories.Newspaper), "FAZ"},getDrawable(R.drawable.faz));
        hashMap.put(new String[]{String.valueOf(Categories.Newspaper), "Spiegel Online"},getDrawable(R.drawable.spiegel));
        hashMap.put(new String[]{String.valueOf(Categories.Interessen), "Politik"},getDrawable(R.drawable.world));
        hashMap.put(new String[]{String.valueOf(Categories.Interessen), "Wirtschaft"},getDrawable(R.drawable.business));
        hashMap.put(new String[]{String.valueOf(Categories.Interessen), "Corona"},getDrawable(R.drawable.coronavirus));
        hashMap.put(new String[]{String.valueOf(Categories.Interessen), "Technik"},getDrawable(R.drawable.tech));
        hashMap.put(new String[]{String.valueOf(Categories.Interessen), "Gaming"},getDrawable(R.drawable.sports));
        hashMap.put(new String[]{String.valueOf(Categories.Interessen), "Sport"},getDrawable(R.drawable.sport));
    }

    // With this method you can easily edit the images of the buttons
    @SuppressLint("ResourceAsColor")
    private void editpictures(){
        for(String[] s: hashMap.keySet()){
            if(!Objects.equals(s[0], String.valueOf(Categories.Newspaper))){
                Objects.requireNonNull(hashMap.get(s)).setTint(R.color.primaryColor);
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
            socialMediaQuellenArrayList.add(new Quellen("",getDrawable(R.drawable.add),Categories.ADDButton));
            newsArrayList.add(new Quellen("",getDrawable(R.drawable.add),Categories.ADDButton));
            interestsArrayList.add(new Quellen("",getDrawable(R.drawable.add),Categories.ADDButton));
    }

    // In this method, depending on a RecyclerView, the recycler view is processed and connected to the adapter
    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Quellen> arrayList) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        AdapterListAddActivity recyclerViewAdapter = new AdapterListAddActivity(this,arrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    //this method is inherited from the onclick listener here and using it we can open the fragment depending on the button clicked
    @Override
    public void onItemClick(Quellen quellen) {
        EditQuellenFragement editQuellenFragement = new EditQuellenFragement();
        editQuellenFragement.setKategorie(quellen.getName());
        editQuellenFragement.setDrawable(quellen.getImage());
        editQuellenFragement.show(getSupportFragmentManager(),"My Fragement");
    }
}