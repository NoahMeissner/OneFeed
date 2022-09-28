package com.example.myapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.data.addSource.SourceAdd;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM sourceAdd")
    LiveData<List<SourceAdd>> getAll();

    @Query("SELECT * FROM sourceAdd WHERE categories IN (:userIds)")
    LiveData<List<SourceAdd>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM sourceAdd WHERE categories LIKE :first AND `notification` LIKE :last LIMIT 1")
    LiveData<SourceAdd> findByName(String first, String last);

    @Update
    void updateList(List<SourceAdd> sourceAdds);

    @Delete
    void delete(List<SourceAdd> sourceAdds);

    @Delete
    void deleteSingle(SourceAdd sourceAdd);

    @Insert
    void insert(SourceAdd sourceAdd);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SourceAdd> sourceAdds);
}
