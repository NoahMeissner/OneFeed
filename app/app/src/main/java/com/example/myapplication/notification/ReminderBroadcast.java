package com.example.myapplication.notification;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import java.text.SimpleDateFormat;
import java.util.Date;

public class ReminderBroadcast extends BroadcastReceiver {

    /*
    This BroadcastReceiver is important if the System calls the Application to send the Push
    Notification
     */

    /*
    Constants
     */
    private final int CHANNEL_ID = 1;


    @Override
    public void onReceive(Context context, Intent intent) {
         @SuppressLint("SimpleDateFormat")
        NotificationList notificationList = new NotificationList(context, getActualHour());
        Notification notification = new Notification(context, "OneFeed",notificationList.getText());
        notification.startNotification(CHANNEL_ID);
    }

    /*
    This Method will give the NotificationList Class the actual Hour
     */
    private String getActualHour(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long time = System.currentTimeMillis();
        Date resultDate = new Date(time);
        return sdf.format(resultDate).substring(0,sdf.format(resultDate).indexOf(":"));
    }
}
