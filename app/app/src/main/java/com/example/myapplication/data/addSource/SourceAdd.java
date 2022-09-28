package com.example.myapplication.data.addSource;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "sourceAdd")
public class SourceAdd {

    /*
    This Method create an object, which is necessary for the ADD Activity
     */

    /*
    Constants
     */
    @PrimaryKey
    @NonNull
    private final String name;
    private final Constants categories;
    private boolean notification;
    private boolean enabled;
    private int imageRessourceID;
    @Ignore
    private boolean setAnimation = false;
    @Ignore
    private Drawable image;
    @Ignore
    // Important for the Notifications to know how much there are
    private int articles;

    /*
    Constructor
     */
    @Ignore
    public SourceAdd(@NonNull String name, Drawable image, Constants categories){
        this.name = name;
        this.image = image;
        this.categories = categories;
    }

    /*
    Constructor for the DataBase
     */
    public SourceAdd(@NonNull String name,
                     Constants categories,
                     boolean notification,
                     int imageRessourceID,
                     boolean enabled
                     ){
        this.imageRessourceID = imageRessourceID;
        this.name = name;
        this.categories = categories;
        this.notification = notification;
        this.enabled = enabled;
    }

    /*
    Getter and setter Methods
     */

    public void setArticles(int articles) {
        this.articles = articles;
    }

    public int getArticles() {
        return articles;
    }

    public int getImageRessourceID() {
        return imageRessourceID;
    }

    public Constants getCategories() {
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
