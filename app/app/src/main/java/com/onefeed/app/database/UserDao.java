package com.onefeed.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.onefeed.app.data.addSource.SourceAdd;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM sourceAdd")
    LiveData<List<SourceAdd>> getAll();

    @Delete
    void delete(List<SourceAdd> sourceAdds);

    @Delete
    void deleteSingle(SourceAdd sourceAdd);

    @Insert
    void insert(SourceAdd sourceAdd);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SourceAdd> sourceAdds);
}
