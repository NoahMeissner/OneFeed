package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.ArrayList;

public class ADDActivity extends AppCompatActivity {

    private ArrayList<Quellen> socialMediaArrayList = new ArrayList<>();
    private ArrayList<Quellen> newsArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
        RecyclerView recylerViewSM = findViewById(R.id.recyclerViewQuellenSM);
        RecyclerView recylerViewNP = findViewById(R.id.recyclerViewQuellenNP);
        initArrayLists();
        initRecyclerView(recylerViewNP,newsArrayList);
        initRecyclerView(recylerViewSM,socialMediaArrayList);
    }



    @SuppressLint("UseCompatLoadingForDrawables")
    private void initArrayLists() {
        socialMediaArrayList.add(new Quellen("Twitter", getDrawable(R.drawable.ic_twitter_brands)));
        socialMediaArrayList.add(new Quellen("Reddit",getDrawable(R.drawable.reddit)));
        socialMediaArrayList.add(new Quellen("",getDrawable(R.drawable.ic_add_button)));
        newsArrayList.add(new Quellen("FAZ",getDrawable(R.drawable.faz)));
        newsArrayList.add(new Quellen("SPIEGEL", getDrawable(R.drawable.spiegel)));
        newsArrayList.add(new Quellen("", getDrawable(R.drawable.ic_add_button)));
    }


    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Quellen> arrayList) {
        // RecylerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerViewAdapter.setQuellenArrayList(arrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
        setClickListener(recyclerView);
    }

    private void setClickListener(RecyclerView recyclerView){
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                EditQuellenFragement editQuellenFragement = new EditQuellenFragement();
                editQuellenFragement.show(getSupportFragmentManager(),"My Fragement");
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }
}