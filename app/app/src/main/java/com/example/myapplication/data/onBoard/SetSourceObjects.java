package com.example.myapplication.data.onBoard;

import com.example.myapplication.R;
import com.example.myapplication.data.addSource.Constants;
import com.example.myapplication.data.addSource.UiElements;
import com.example.myapplication.data.addSource.SourceAdd;
import com.example.myapplication.database.InitialData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SetSourceObjects {

    /*
    This Method will hand over all Sources to the DataBase
     */

    /*
    Constants
     */

    // These Constants will be hand over from the Constructor
    private final InitialData data;
    private final boolean notification;
    // This Arraylist will be necessary to safe all new Source Add Objects
    private final ArrayList<SourceAdd> sourceResult = new ArrayList<>();
    // This boolean is necessary to initialise a new SourceAdd Object
    private final boolean enabled = true;
    /*
    This new AddActivityIcon will hand over the pictureIDHashMap and must be outside of a Method
    to prevent that this HashMap will not be hand over multiple Times
     */
    UiElements uiElements = new UiElements();
    HashMap<String, Integer> pictureHashMap = Objects.requireNonNull(
            uiElements.getPictureHashMap());


    /*
    Constructor
     */
    public SetSourceObjects(InitialData data,
                            boolean notification){
        this.data = data;
        this.notification = notification;
    }

    /*
    This Method will check, which Category is called and call the right Method
     */
    private void setArrayListComponents(ArrayList<String> arrayList, Constants category) {
        if (category == Constants.Interests) {
            setInterests(arrayList);
        }
        if (category == Constants.SocialMedia) {
            setSocialMedia(arrayList);
        }
    }

    /*
    This Method will set new  Interests SourceAdd Elements and
     hand over those Elements to the Array List
     */
    private void setInterests(ArrayList<String> arrayList){
        for(String interestName : arrayList){
            SourceAdd sourceAdd = new SourceAdd(interestName,
                    Constants.Interests,
                    notification,
                    getImageID(interestName),
                    enabled);
            sourceResult.add(sourceAdd);
        }
    }

    /*
   This Method will set new  Social Media SourceAdd Elements and
    hand over those Elements to the Array List
    */
    private void setSocialMedia(ArrayList<String> arrayList){
        for(String socialMediaName : arrayList) {
            SourceAdd sourceAdd = new SourceAdd(socialMediaName,
                    Constants.SocialMedia,
                    notification,
                    getImageID(socialMediaName),
                    enabled);
            sourceResult.add(sourceAdd);
        }
    }




    /*
    This Method will query all Image ID's and will hand over those to the Source Add Elements
     */
    private int getImageID(String s) {
        return Objects.requireNonNull(pictureHashMap.get(s));
    }

    /*
  This Method will set all News SourceAdd Elements and
   hand over those Elements to the Array List
   */
    public void setNews(){
        SourceAdd sourceFAZ = new SourceAdd(
                Constants.news.FAZ.name(),
                Constants.Newspaper,notification,
                R.drawable.faz,
                enabled);

        SourceAdd sourceSpiegel = new SourceAdd(
                Constants.news.Spiegel.name(),
                Constants.Newspaper,
                notification,
                R.drawable.spiegel,
                enabled);

        sourceResult.add(sourceFAZ);
        sourceResult.add(sourceSpiegel);
    }

    public void setSocialMediaList(ArrayList<String> socialMediaList) {
        setArrayListComponents(socialMediaList, Constants.SocialMedia);
    }

    public void setInterestsList(ArrayList<String> interestsList) {
        setArrayListComponents(interestsList, Constants.Interests);
    }

    /*
    This Method will add the Array List to the DataBase
    */
    public void addToDataBase(){
        data.setArrayList(sourceResult);
    }
}
