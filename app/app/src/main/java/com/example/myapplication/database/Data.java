package com.example.myapplication.database;

import com.example.myapplication.data.addSource.SourceAdd;

import java.util.ArrayList;

public class Data {

    private ArrayList<SourceAdd> interests = new ArrayList<>();

    private ArrayList<SourceAdd> newsPaper = new ArrayList<>();

    private ArrayList<SourceAdd> socialMedia = new ArrayList<>();

    public Data(){

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
