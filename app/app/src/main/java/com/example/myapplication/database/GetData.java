package com.example.myapplication.database;

import android.content.Context;

import com.example.myapplication.data.addSource.Category;
import com.example.myapplication.data.addSource.SourceAdd;

import java.util.ArrayList;

public class GetData implements DataBaseHelper.initializeProcessFinish {

    private final Context context;

    private ArrayList<SourceAdd> sourceAdds = new ArrayList<>();

    private DataBaseHelper dataBaseHelper;

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

    @Override
    public void getDataBase(ArrayList<SourceAdd> sourceAdds) {
        this.sourceAdds=sourceAdds;
    }
}
