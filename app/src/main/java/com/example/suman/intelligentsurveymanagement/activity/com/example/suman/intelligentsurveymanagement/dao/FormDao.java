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

    @Query("SELECT * FROM forms where form_status = 'sent'")
    List<Form> getSentForms();

    @Query("SELECT * FROM forms where form_status = 'inbox'")
    List<Form> getInboxForms();

    @Query("SELECT * FROM forms WHERE form_id = :id")
    Form getFormById(int id);

    @Insert
    void insertForm(Form form);

    @Delete
    void deleteForm(Form form);

    @Update
    void updateForm(Form form);

}
