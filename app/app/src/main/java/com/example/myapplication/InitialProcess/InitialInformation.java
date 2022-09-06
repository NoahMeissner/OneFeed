package com.example.myapplication.InitialProcess;


import androidx.annotation.NonNull;

import java.util.ArrayList;

public class InitialInformation {
/*
With this class, all information of the initialization process can be stored bundled in the home feed class
and do not have to be saved separately
 */

    private ArrayList<String> stringInterestsList;
    private boolean notifi;
    private boolean consum;
    private ArrayList<String> socialMedia;


    public InitialInformation(@NonNull ArrayList<String> stringInterestsList,@NonNull boolean notifi,@NonNull boolean consum, ArrayList<String> socialMedia){
        this.consum = consum;
        this.notifi = notifi;
        this.socialMedia = socialMedia;
        this.stringInterestsList = stringInterestsList;
    }

    public ArrayList<String> getStringInterestsList() {
        return stringInterestsList;
    }

    public boolean isNotifi() {
        return notifi;
    }

    public boolean isConsum() {
        return consum;
    }


    public ArrayList<String> getSocialMedia() {
        return socialMedia;
    }

    public void setStringInterestsList(ArrayList<String> stringInterestsList) {
        this.stringInterestsList = stringInterestsList;
    }

    public void setNotifi(boolean notifi) {
        this.notifi = notifi;
    }

    public void setConsum(boolean consum) {
        this.consum = consum;
    }

    public void setSocialMedia(ArrayList<String> socialMedia) {
        this.socialMedia = socialMedia;
    }
}
