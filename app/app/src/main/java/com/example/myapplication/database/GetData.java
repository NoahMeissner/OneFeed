package com.example.myapplication.database;

import android.content.Context;

import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.addSource.SourceAdd;

import java.util.ArrayList;

public class GetData implements DataBaseHelper.initializeProcessFinish {

    /*
    With This Method you will get Access to the Information from the DataBase
     */

    /*
    Constants
     */
    private final Context context;
    private ArrayList<SourceAdd> sourceAdds = new ArrayList<>();
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

    public ArrayList<SourceAdd> getCategory(Category category){
        ArrayList<SourceAdd> result = new ArrayList<>();
        for(SourceAdd sourceAdd: sourceAdds){
            if(sourceAdd.getCategories()== category){
                result.add(sourceAdd);
            }
        }
        return result;
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
    public void getDataBase(ArrayList<SourceAdd> sourceAdds) {
        this.sourceAdds=sourceAdds;
    }
}
