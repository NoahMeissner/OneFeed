package com.example.myapplication.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.myapplication.data.addSource.Constants;
import com.example.myapplication.data.addSource.SourceAdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DataBaseHelper {

    /*
    This class Helps to get access to the DataBase from every Activity where the Data is needed.
     */


    // Constants
    private final UserDao userDao;
    private final LiveData<List<SourceAdd>> sourceArrayList;

    /*
    This Method is there for the getData Class
     */
    public DataBaseHelper(Context context){
        this.userDao = AppDataBase.getDatabase(context).userDao();
        this.sourceArrayList = userDao.getAll();
    }

    public void deleteSingleItem(SourceAdd sourceAdd){
        Runnable r = () -> userDao.deleteSingle(sourceAdd);
        AppDataBase.databaseWriteExecutor.execute(r);
    }

    public HashMap<Constants, ArrayList<SourceAdd>> getAll(List<SourceAdd> sources){
        /*
       Initialise HashMap
         */
        HashMap<Constants,ArrayList<SourceAdd>> result = new HashMap<>();

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
        return  result;
    }


    /*
    With this Method you are able to delete one Item from the Database
     */
    public void removeItems(ArrayList<SourceAdd> removeList){
        Runnable r = () -> userDao.delete(removeList);
        AppDataBase.databaseWriteExecutor.execute(r);
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
        AppDataBase.databaseWriteExecutor.execute(r);
    }

    /*
    With this Method there is the possibility to add only one new item
     */
    public void insertSourceItem(SourceAdd sourceAdd){
        Runnable r = () -> userDao.insert(sourceAdd);
        AppDataBase.databaseWriteExecutor.execute(r);
    }
}

