package com.example.myapplication.notification;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.myapplication.R;
import com.example.myapplication.data.addSource.Constants;
import com.example.myapplication.data.addSource.SourceAdd;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class NotificationList {

    /*
    Constants
     */
    // Overhand the Constructor
    private final String hour;
    private final Context context;
    // get from the String values
    private final String text;
    // Lists
    private final String[] timeList;
    private String[] notificationItems;
    // Preferences to edit the DATA Base
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editPreferences;


    /*
    Constructor
     */
    public NotificationList(Context context, String hour){
        this.context = context;
        this.hour = hour;
        text = context.getString(R.string.notification_text);
        timeList = Objects.requireNonNull(context).getResources()
                .getStringArray(R.array.notification_time_list);
        initSharedPreferences();
        getNotificationItems();
    }

    /*
    Get the Notification Items from the Shared Preferences
     */
    private void getNotificationItems() {
        HashSet<String> inputList = (HashSet<String>) sharedPreferences
                .getStringSet( Constants.initial.NotificationList.name(),new HashSet<>());
        notificationItems = new String[inputList.size()];
        inputList.toArray(notificationItems);
    }

    /*
    Get the List from the DataBase
     */
    private void initSharedPreferences() {
        sharedPreferences = context.getSharedPreferences(
                context.getResources()
                        .getString(R.string.initProcesBoolean), 0);
        editPreferences = sharedPreferences.edit();
    }

    // Overhand the Text to another Class
    public String getText(){
        if(getList().get(Integer.parseInt(hour))!= null){
            return text.replace("{Quelle}", Objects.requireNonNull(getList().get(Integer.parseInt(hour))));
        }
        else{
            return null;
        }
    }

    /*
    This Method makes a HashMap with the Items from the DataBase and the TimeSlots to select
    for each Time Slot another Source
     */
    private HashMap<Integer,String> getList() {
        HashMap<Integer,String> notificationTypePerTime = new HashMap<>();
        if(timeList.length<=notificationItems.length) {
            for (int i = 0; i < timeList.length; i++) {
                notificationTypePerTime.put(
                        Integer.valueOf(
                                timeList[i].substring(0, timeList[i].indexOf(":")))
                        , notificationItems[i]);
            }
        }
        else{
            for (int i = 0; i < notificationItems.length; i++) {
                notificationTypePerTime.put(
                        Integer.valueOf(
                                timeList[i].substring(0, timeList[i].indexOf(":")))
                        , notificationItems[i]);
            }
        }
        return notificationTypePerTime;
    }

    /*
    This Method will set the Sources which are allowed to get a Notification to a List to
    the DataBase
    Important: if the List must be update or the List equals null
     */
    public void setSourceList(List<SourceAdd> sourceAddArrayList){
        HashSet<String> result = new HashSet<>();
        for(SourceAdd sources: sourceAddArrayList){
            if(sources.isNotification()){
                result.add(sources.getName());
            }
        }
        editPreferences.putStringSet(Constants.initial.NotificationList.name(),result);
        editPreferences.apply();
    }
}
