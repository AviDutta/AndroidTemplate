package com.claricetechnologies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClDatabase extends SQLiteOpenHelper {

    // With every change in the database schema, the database
    // version has to be incremented.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "template.db";

    public ClDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete the old database entries and create the new database
        onCreate(db);
    }
}
