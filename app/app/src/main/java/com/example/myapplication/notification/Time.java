package com.example.myapplication.notification;

import android.util.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class Time extends TimerTask {

    /*
    Constants
     */
    private final NotificationInterface listener;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final String[] notificationTimes = {"08:00","10:00","12:00","16:00","20:00"};

    /*
    Constructor
     */
    public Time(NotificationInterface listener){
        this.listener = listener;
        run();
    }


    /*
    This Method checks if the dateTimeFormatter equals the notificationTime
    which is declared in the notificationTimes[]
     */
    @Override
    public void run() {
        try{
            for(int i = 0; i<notificationTimes.length;i++){
                if(notificationTimes[i].equals(dateTimeFormatter.format(LocalDateTime.now()))){
                    // This Listener will be inform the Service that it
                    // is time to push a Notification
                    listener.notify(i);
                }
            }
        }
        catch (Exception e){
            Log.d("Timer", String.valueOf(e));
        }
    }

    /*
    Interface to use it as a listener
     */
    public interface NotificationInterface {
        void notify(int i);
    }
}
