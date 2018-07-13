package com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.entity.Form;

import java.util.List;

@Dao
public interface FormDao {

    @Query("SELECT * FROM forms")
    List<Form> getAllForms();

    @Insert
    void insertForm(Form form);

    @Delete
    void deleteForm(Form form);

    @Update
    void updateForm(Form form);

}
