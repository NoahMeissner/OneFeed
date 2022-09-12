package com.example.myapplication.addNewQuelle;

import android.graphics.drawable.Drawable;


public class SourceAdd {

    /*
    This Method create an object, which is necessary for the ADD Activity
     */

    private final Drawable image;
    private final String name;
    private final Categories categories;
    private boolean notification;
    private boolean enabled;
    private boolean setAnimation = false;

    public SourceAdd(String name, Drawable image, Categories categories){
        this.name = name;
        this.image = image;
        this.categories = categories;
    }

    public Categories getCategories() {
        return categories;
    }

    public boolean isNotification() {
        return notification;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Drawable getImage() {
        return image;
    }

    public void setSetAnimation(boolean setAnimation) {
        this.setAnimation = setAnimation;
    }

    public boolean getAnimation() {
        return setAnimation;
    }

    public String getName() {
        return name;
    }
}
