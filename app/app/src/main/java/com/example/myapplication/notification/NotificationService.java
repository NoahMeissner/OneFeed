package com.example.myapplication.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.myapplication.R;
import com.example.myapplication.api.NewsRepository;
import com.example.myapplication.api.rss.RssUrls;
import com.example.myapplication.data.addSource.Constants;
import com.example.myapplication.data.addSource.SourceAdd;
import com.example.myapplication.data.addSource.UiElements;
import com.example.myapplication.database.GetData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotificationService extends Service {

    /*
    This Class will inform the User if there are new News for their interests
     */

    private final LocalDateTime time = LocalDateTime.now();
    LiveData<List<SourceAdd>> list;
    GetData getData;
    HashMap<Constants.news,Integer> result = new HashMap<>();
    HashMap<Constants.news,List<String>> newsList = new HashMap<>();






    public NotificationService(){

    }

    private void apiRequest() {
        NewsRepository newsRepository = new NewsRepository(this);
        newsRepository.loadNews();
        setNotifications();
    }

    private final String CHANNEL_ID = "2";
    private void setNotifications(){
        String text = "Sie haben für Spiegel Online $NUMBER$ Nachrichten";
        UiElements uiElements = new UiElements();
        uiElements.initialPictureHashMap();
        int counter = 1;
        for(Constants.news news:result.keySet()){
            text.replace("$NUMBER$",String.valueOf(result.get(news)));
            Notification notification = new Notification(
                    uiElements.getPictureId(news.name()),
                    this,
                    "OneFeed Notification",text);
            notification.startNotification(notificationManager(),counter);
            counter++;
        }
    }

    private NotificationManager notificationManager (){
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel
                (CHANNEL_ID,"OneFEED", importance);

        NotificationManager notificationManager = this
                .getSystemService(NotificationManager.class);

        notificationManager.createNotificationChannel(channel);
        return notificationManager;
    }

    /*
    Check if the User allows to send Notifications
     */
    private void checkNotificationPermission() {
        SharedPreferences preferences = getBaseContext().getSharedPreferences(getBaseContext().getResources()
                .getString(R.string.initProcesBoolean), 0);
        boolean permission = preferences.getBoolean(Constants.initial.Notification.name(), false);
        if(!permission){
            onDestroy();
        }
        setLists();
    }

    private void setLists() {
        RssUrls rssUrls = new RssUrls();
        ArrayList<Constants.interests> interests = new ArrayList<>();
        if(checkIndividualPermission()!= null) {
            for (SourceAdd source : checkIndividualPermission()) {
                if (source.getCategories() == Constants.Newspaper) {
                    newsList.put(Constants.news.valueOf(source.getName()), new ArrayList<>());
                }
                if (source.getCategories() == Constants.Interests) {
                    interests.add(Constants.interests.valueOf(source.getName()));
                }

            }
            for (Constants.news news : newsList.keySet()) {
                rssUrls.getUrls(interests, news);
            }
        }
        apiRequest();
    }

    private ArrayList<SourceAdd> checkIndividualPermission() {
        LiveData<List<SourceAdd>> overhandedData = getData.getSourceAdds();
        ArrayList<SourceAdd> permissionAllowed = new ArrayList<>();
        if(overhandedData != null && overhandedData.getValue()!=null){
            for(SourceAdd source: overhandedData.getValue()){
                if (source.isNotification()){
                    permissionAllowed.add(source);
                }
            }
        }
        return permissionAllowed;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getData = new GetData(getBaseContext());
        result.put(Constants.news.FAZ,5);
       result.put(Constants.news.Spiegel,10);
        checkNotificationPermission();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
