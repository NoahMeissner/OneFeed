package com.example.myapplication.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.myapplication.data.addSource.Constants;
import com.example.myapplication.data.addSource.SourceAdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetData implements DataBaseHelper.initializeProcessFinish {

    /*
    With This Method you will get Access to the Information from the DataBase
     */

    /*
    Constants
     */
    private final Context context;
    private LiveData<List<SourceAdd>> sourceAdds;
    private DataBaseHelper dataBaseHelper;

    /*
    Constructor
     */
    public GetData(Context context){
        this.context = context;
        initDataBase();
    }

    private void initDataBase() {
        dataBaseHelper = new DataBaseHelper(context, this);
    }


    public void update(ArrayList<SourceAdd> updateArrayList){
        dataBaseHelper.removeItems(updateArrayList);
        dataBaseHelper.insertDataBase(updateArrayList);
    }

    public ArrayList<SourceAdd> getCategory(Constants category, List<SourceAdd> sources){
        ArrayList<SourceAdd> result = new ArrayList<>();
        HashMap<Constants,ArrayList<SourceAdd>> dataBase = dataBaseHelper.getAll(sources);
        try {
            result = dataBase.get(category);
        }
        finally {
            return result;
        }
    }

    public HashMap<Constants,ArrayList<SourceAdd>> getAll(List<SourceAdd> sources){
        return dataBaseHelper.getAll(sources);
    }

    public void removeSource(SourceAdd sourceADD){
        dataBaseHelper.deleteSingleItem(sourceADD);
    }

    public void InsertSource(SourceAdd sourceAdd){
        dataBaseHelper.insertSourceItem(sourceAdd);
    }

    /*
    This Method is a Listener from the DataBaseHelper to get the Information after the DataBase has
    started
     */
    @Override
    public void getDataBase(LiveData<List<SourceAdd>> sourceAdds) {
        this.sourceAdds=sourceAdds;
    }

    public LiveData<List<SourceAdd>> getSourceAdds() {
        return sourceAdds;
    }
}
