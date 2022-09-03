package com.example.myapplication.addNewQuelle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.addNewQuelle.adapter.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ADDActivity extends AppCompatActivity implements Adapter.OnItemClickListener {


    private final ArrayList<Quellen> socialMediaQuellenArrayList = new ArrayList<>();
    private final ArrayList<Quellen> newsArrayList = new ArrayList<>();
    private final ArrayList<Quellen> interestsArrayList = new ArrayList<>();
    private final HashMap<String[],Drawable> hashMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_addactivity);
        RecyclerView recylerViewSM = findViewById(R.id.recyclerViewQuellenSM);
        RecyclerView recylerViewNP = findViewById(R.id.recyclerViewQuellenNP);
        RecyclerView recyclerViewIn = findViewById(R.id.recyclerViewQuellenIn);
        initHashMap();
        editpictures();
        initQuellen();
        initRecyclerView(recylerViewNP,newsArrayList);
        initRecyclerView(recylerViewSM,socialMediaQuellenArrayList);
        initRecyclerView(recyclerViewIn,interestsArrayList);
    }





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

    @SuppressLint("ResourceAsColor")
    private void editpictures(){
        for(String[] s: hashMap.keySet()){
            if(!Objects.equals(s[0], String.valueOf(Categories.Newspaper))){
                Objects.requireNonNull(hashMap.get(s)).setTint(R.color.primaryColor);
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initQuellen(){
        for(String[]s : hashMap.keySet()){
            if(s[0].equals(String.valueOf(Categories.SocialMedia))){
                socialMediaQuellenArrayList.add(new Quellen(s[1],hashMap.get(s)));
            }
            if(s[0].equals(String.valueOf(Categories.Newspaper))){
                newsArrayList.add(new Quellen(s[1],hashMap.get(s)));
            }
            if(s[0].equals(String.valueOf(Categories.Interessen))){
                interestsArrayList.add(new Quellen(s[1],hashMap.get(s)));
            }

        }
            socialMediaQuellenArrayList.add(new Quellen("",getDrawable(R.drawable.add)));
            newsArrayList.add(new Quellen("",getDrawable(R.drawable.add)));
            interestsArrayList.add(new Quellen("",getDrawable(R.drawable.add)));
    }

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Quellen> arrayList) {
        // RecylerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        Adapter recyclerViewAdapter = new Adapter(this,arrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onItemClick(Quellen quellen) {
        System.out.println("Hello");
    }
}