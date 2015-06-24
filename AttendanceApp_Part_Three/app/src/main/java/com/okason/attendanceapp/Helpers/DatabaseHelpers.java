package com.okason.attendanceapp.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Valentine on 6/23/2015.
 */
public class DatabaseHelpers extends SQLiteOpenHelper {


    public DatabaseHelpers(Context context) {
        super(context, "attendanceApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CUSTOMER_TABLE);

    }

    private static final String CREATE_CUSTOMER_TABLE =
            "CREATE TABLE " + "Attendants" + "(" +
                    "_id" + " INTEGER PRIMARY KEY, " +
                    "Name" + " TEXT NOT NULL, " +
                    "Email" + " TEXT NOT NULL, " +
                    "Phone" + " TEXT, " +
                    "Street Address" + " TEXT, " +
                    "City" + " TEXT, " +
                    ")";



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
