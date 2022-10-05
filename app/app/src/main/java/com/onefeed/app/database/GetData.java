package com.onefeed.app.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.onefeed.app.data.addSource.Constants;
import com.onefeed.app.data.addSource.SourceAdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetData {

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
        dataBaseHelper = new DataBaseHelper(context);
        sourceAdds = dataBaseHelper.getSourceArrayList();
    }


    public void update(ArrayList<SourceAdd> updateArrayList){
        dataBaseHelper.removeItems(updateArrayList);
        dataBaseHelper.insertDataBase(updateArrayList);
    }

    public ArrayList<SourceAdd> getCategory(Constants category, List<SourceAdd> sources){
        ArrayList<SourceAdd> result;
        HashMap<Constants,ArrayList<SourceAdd>> dataBase = dataBaseHelper.getAll(sources);
        result = dataBase.get(category);
        return result;
    }

    public void removeSource(SourceAdd sourceADD){
        dataBaseHelper.deleteSingleItem(sourceADD);
    }

    public void InsertSource(SourceAdd sourceAdd){
        dataBaseHelper.insertSourceItem(sourceAdd);
    }

    public LiveData<List<SourceAdd>> getSourceAdds() {
        return sourceAdds;
    }
}
