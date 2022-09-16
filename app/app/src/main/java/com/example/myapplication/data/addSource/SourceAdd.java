package com.example.myapplication.data.addSource;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.myapplication.R;

@Entity(tableName = "sourceAdd")
public class SourceAdd {

    /*
    This Method create an object, which is necessary for the ADD Activity
     */

    @PrimaryKey
    @NonNull
    private final String name;
    private final Category categories;
    private boolean notification;
    private boolean enabled;
    @Ignore
    private boolean setAnimation = false;
    @Ignore
    private Drawable image;
    @Ignore
    private int imagePath = R.drawable.ic_launcher_foreground;

    @Ignore
    public SourceAdd(@NonNull String name, Drawable image, Category categories){
        this.name = name;
        this.image = image;
        this.categories = categories;
    }

    public SourceAdd(@NonNull String name,
                     Category categories,
                     boolean notification,
                     boolean enabled
                     ){
        // this.imagePath = imagePath;
        this.name = name;
        this.categories = categories;
        this.notification = notification;
        this.enabled = enabled;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImage(Drawable image){
        this.image = image;
    }

    public Category getCategories() {
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

    @NonNull
    public String getName() {
        return name;
    }
}
