package com.example.myapplication.addNewQuelle;

import android.graphics.drawable.Drawable;

public class Quellen {

    private final Drawable image;
    private final String name;

    public Quellen(String name, Drawable image){
        this.name = name;
        this.image = image;
    }

    public Drawable getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
