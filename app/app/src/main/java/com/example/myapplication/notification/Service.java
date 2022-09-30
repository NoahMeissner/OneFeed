package com.example.myapplication.notification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;


import com.example.myapplication.R;
import com.example.myapplication.data.addSource.Constants;

import java.util.HashSet;
import java.util.Timer;

public class Service extends android.app.Service {

    /*
    This Service will send an Push Notification to inform the User about possible new News
    from his sources or interests

    It is important that the notifications will not annoy the User
        That is the reason if one Push Notification was send the Service stops and will restart
        when the User will start the Application
     */

    /*
    Constants
     */
    private HashSet<String> notificationList = new HashSet<>();
    private Timer timer = new Timer();
    private final int delay = 60000;


    /*
    This Method handles the Service
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.schedule(new Time(i -> {
                    getList(i);
                    stopTimerTask();
                    stopSelf();
        }),0, delay);

        return START_STICKY;
    }

    /*
    This Method will get the List from the Shared Preferences
     */
    private void getList(int i) {
        SharedPreferences preferences = getSharedPreferences(getResources()
                .getString(R.string.initProcesBoolean),0);
        notificationList = (HashSet<String>) preferences.getStringSet(Constants
                .initial
                .NotificationList
                .name(),
                new HashSet<>());
        setNotifications(i);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
    This Method will set the Notification get the Name from the List  {Example: Source or Interest}
    and push the Notification
     */
    private void setNotifications(int index){
        String[] list = notificationList.toArray(new String[notificationList.size()]);
        String text = this.getResources().getString(R.string.notificationText);
        int channel = 1;
        if(list.length>index){
            String notificationText = text.replace("{Quelle}",list[index]);
            Notification notification = new Notification(
                    this,
                    notificationText,"");
            notification.startNotification(channel);
        }
    }

    /*
    This Method stops the Timer Task
     */
    public void stopTimerTask() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    /*
    If the Timer was Destroyed the Service is not necessary anymore-> on Destroy
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimerTask();
        Log.d("Destroy","Destroy");
    }
}
