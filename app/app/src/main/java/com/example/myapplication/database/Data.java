package com.example.myapplication.database;

import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.addSource.SourceAdd;

import java.util.ArrayList;

public class Data {

    private ArrayList<SourceAdd> interests;

    private ArrayList<SourceAdd> newsPaper;

    private ArrayList<SourceAdd> socialMedia;

    private ArrayList<Category.interests> selectedInterests = new ArrayList<>();


    public Data(){

    }

    public void setSelectedInterests(ArrayList<Category.interests> selectedInterests) {
        this.selectedInterests = selectedInterests;
    }

    public ArrayList<Category.interests> getSelectedInterests() {
        if (interests == null){
            return selectedInterests;
        }
        return null;
    }

    public void setNewsPaper(ArrayList<SourceAdd> newsPaper) {
        this.newsPaper = newsPaper;
    }

    public void setSocialMedia(ArrayList<SourceAdd> socialMedia) {
        this.socialMedia = socialMedia;
    }

    public ArrayList<SourceAdd> getNewsPaper() {
        return newsPaper;
    }

    public ArrayList<SourceAdd> getSocialMedia() {
        return socialMedia;
    }

    public ArrayList<SourceAdd> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<SourceAdd> interests) {
        this.interests = interests;
    }
}
