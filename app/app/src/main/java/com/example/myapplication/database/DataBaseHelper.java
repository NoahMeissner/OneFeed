package com.example.myapplication.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.myapplication.data.addSource.Constants;
import com.example.myapplication.data.addSource.SourceAdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlinx.coroutines.scheduling.Task;

public class DataBaseHelper {

    /*
    This class Helps to get access to the DataBase from every Activity where the Data is needed.
     */


    // Constants
    private final ExecutorService service = Executors.newScheduledThreadPool(1);
    private UserDao userDao;
    private NewsReadDao newsReadDao;
    private LiveData<List<SourceAdd>> sourceArrayList;
    private final Context context;
    private AppDataBase dp;
    private initializeProcessFinish initializeProcessfinish;

    /*
    This constructor will be there for the InitialData Class
     */
    public DataBaseHelper(Context context){
        this.context = context;
        initDatabase();
    }

    /*
    This Method is there for the getData Class
     */
    public DataBaseHelper(Context context, initializeProcessFinish initializeProcessfinish){
        this.context = context;
        this.initializeProcessfinish = initializeProcessfinish;
        initDatabase();
    }

    public void deleteSingleItem(SourceAdd sourceAdd){
        Runnable r = () -> userDao.deleteSingle(sourceAdd);
        service.execute(r);
    }


    /*
    This class initial the DataBase
     */
    public void initDatabase() {
        Thread init = new Thread(() -> {
            try {
                String DATABASE_NAME = "sourceAdd";
                dp = AppDataBase.getDatabase(context);
                userDao = dp.userDao();
                newsReadDao = dp.newsReadDao();
            }
            finally {
                sourceArrayList = userDao.getAll();
                if(sourceArrayList!= null && initializeProcessfinish != null){
                    initializeProcessfinish.getDataBase(sourceArrayList);
                }
            }
        });
        init.start();
    }

    public HashMap<Constants, ArrayList<SourceAdd>> getAll(List<SourceAdd> sources){
        /*
       Initialise HashMap
         */
        HashMap<Constants,ArrayList<SourceAdd>> result = new HashMap<>();
        try{
            result.put(Constants.SocialMedia,new ArrayList<>());
            result.put(Constants.Interests,new ArrayList<>());
            result.put(Constants.Newspaper,new ArrayList<>());
    /*
    Check the DataBase and put Items in order
     */
            for(SourceAdd sourceAdd: sources){
                if(sourceAdd.getCategories() == Constants.Newspaper){
                    Objects.requireNonNull(result.get(Constants.Newspaper)).add(sourceAdd);
                }
                if(sourceAdd.getCategories() == Constants.SocialMedia){
                    Objects.requireNonNull(result.get(Constants.SocialMedia)).add(sourceAdd);
                }
                if(sourceAdd.getCategories() == Constants.Interests){
                    Objects.requireNonNull(result.get(Constants.Interests)).add(sourceAdd);
                }
            }
        }
        finally {
            return  result;
        }
    }


    /*
    With this Method you are able to delete one Item from the Database
     */
    public void removeItems(ArrayList<SourceAdd> removeList){
        Runnable r = () -> userDao.delete(removeList);
        service.execute(r);
    }

    /*
    If you have changes in the DataBase and need the changed Array List you can get the Array List
    with this Method
     */
    public LiveData<List<SourceAdd>> getSourceArrayList() {
        return sourceArrayList;
    }

    /*
    With this Method there is the possibility to add new Elements to the DataBase
    */
    public void insertDataBase(ArrayList<SourceAdd> sourceArrayList){
        Runnable r = () -> userDao.insertAll(sourceArrayList);
        service.execute(r);
    }

    /*
    With this Method there is the possibility to add only one new item
     */
    public void insertSourceItem(SourceAdd sourceAdd){
        Runnable r = () -> userDao.insert(sourceAdd);
        service.execute(r);
    }

    /*
    This Method implements the Listener for the DataBaseHelper
     */
    public interface initializeProcessFinish {
        void getDataBase(LiveData<List<SourceAdd>> sourceAdds);
    }

    public NewsReadDao getNewsReadDao() {
        return newsReadDao;
    }
}

