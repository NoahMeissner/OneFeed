package com.example.myapplication.notification;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.addSource.SourceAdd;
import com.example.myapplication.database.GetData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class NotificationService extends Service {

    /*
    This Class will inform the User if there are new News for their interests
     */

    private final LocalDateTime time = LocalDateTime.now();
    private final GetData getData;
    private final HashMap<Category.interests,ArrayList<SourceAdd>> hashMapNotifications = new HashMap<>();


    public NotificationService(){
        checkNotificationPermission();
        getData = new GetData(this);
        setListWithSources();
    }

    /*
    Check if the User allows to send Notifications
     */
    private void checkNotificationPermission() {
        SharedPreferences preferences = this.getSharedPreferences(this.getResources()
                .getString(R.string.initProcesBoolean), 0);
        boolean permission = preferences.getBoolean(Category.initial.Notification.name(), false);
        if(!permission){
            onDestroy();
        }
    }

    /*
    Set List to handle the Notifications
     */
    private void setListWithSources() {
        HashMap<Category, ArrayList<SourceAdd>> hashMapDatabase = getData.getAll();
        /*
        Initial hashMap Notifications
         */
        for(SourceAdd sourceAdd: Objects.requireNonNull(hashMapDatabase.get(Category.Interests))){
            if(sourceAdd.isNotification()){
                /*
                 It is important to filter Sources out which haven`t a the Permission
                 to send Notifications
                 */
                hashMapNotifications.put(Category.interests
                        .valueOf(sourceAdd.getName()),new ArrayList<>());
            }
        }
        /*
        This for Loop add all Sources to the Interests, which give the possibility to put
        all Sources in order to the Interests
         */
        for(Category.interests interests:hashMapNotifications.keySet()){
            for(SourceAdd sourceAdd: Objects.requireNonNull(hashMapDatabase
                    .get(Category.SocialMedia))){

                if(sourceAdd.isNotification()){
                    Objects.requireNonNull(hashMapNotifications.get(interests)).add(sourceAdd);
                }
            }
            for(SourceAdd sourceAdd: Objects.requireNonNull(hashMapDatabase
                    .get(Category.Newspaper))){

                if(sourceAdd.isNotification()){
                    Objects.requireNonNull(hashMapNotifications.get(interests)).add(sourceAdd);
                }
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
