package com.example.myapplication.addNewQuelle;

import android.graphics.drawable.Drawable;

public class Quellen {

    /*
    This Method create an object, which is necessary for the ADD Activity
     */

    private final Drawable image;
    private final String name;
    private Categories categories;
    private boolean notification;
    private boolean enabeld;

    public Quellen(String name, Drawable image,Categories categories){
        this.name = name;
        this.image = image;
    }

    public Categories getCategories() {
        return categories;
    }

    public boolean isNotification() {
        return notification;
    }

    public boolean isEnabeld() {
        return enabeld;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }


    public void setEnabeld(boolean enabeld) {
        this.enabeld = enabeld;
    }

    public Drawable getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
