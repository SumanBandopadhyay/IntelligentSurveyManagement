package com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.dao.FormDao;
import com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.entity.Form;

@Database(entities = {Form.class}, version = AppDatabase.DB_VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public static final int DB_VERSION = 1;

    public static final String DATABASE_NAME = "formdb";

    private static AppDatabase INSTANCE;

    public abstract FormDao formDao();

    private final MutableLiveData<Boolean> IS_DATABASE_CREATED = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context.getApplicationContext());
                    INSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(Context applicationContext) {
        if (applicationContext.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        IS_DATABASE_CREATED.postValue(true);
    }

    private static AppDatabase buildDatabase(final Context applicationContext) {
        return Room.databaseBuilder(applicationContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }
                }).build();
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return IS_DATABASE_CREATED;
    }

}
