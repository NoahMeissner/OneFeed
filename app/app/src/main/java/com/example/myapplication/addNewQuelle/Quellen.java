package com.example.myapplication.addNewQuelle;

import android.graphics.drawable.Drawable;

public class Quellen {

    /*
    This Method create an object, which is necessary for the ADD Activity
     */

    private final Drawable image;
    private final String name;
    private Categories categories;

    public Quellen(String name, Drawable image,Categories categories){
        this.name = name;
        this.image = image;
    }

    public Categories getCategories() {
        return categories;
    }

    public Drawable getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
