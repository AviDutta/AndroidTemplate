package com.claricetechnologies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ClDatabaseHelper {

    private static ClDatabaseHelper mDatabaseHelper;
    private Context mContext;
    private ClDatabase mDatabase;
    private SQLiteDatabase mDb;
    private ContentValues mContentValues;

    private ClDatabaseHelper(Context context) {
        // Singleton implementation.
        mContext = context;
        mDatabase = new ClDatabase(context);
    }

    public static synchronized ClDatabaseHelper getInstance(Context context) {
        if (null == mDatabaseHelper) {
            synchronized (ClDatabaseHelper.class) {
                // Double checked singleton lazy initialization.
                mDatabaseHelper = new ClDatabaseHelper(context);
            }
        }
        return mDatabaseHelper;
    }

    //---open SQLite DB--
    public ClDatabaseHelper open() throws SQLException {
        mDb = mDatabase.getWritableDatabase();
        return this;
    }

    //---close SQLite DB---
    public void close() {
        mDatabaseHelper.close();
    }
}