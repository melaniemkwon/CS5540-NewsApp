package com.example.android.newsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by melaniekwon on 7/23/17.
 */

// HW3: 3. Create DBHelper that extends SQLiteOpenHelper class
public class DBHelper extends SQLiteOpenHelper {

    // Create a static final String called DATABASE_NAME and set it to "newsitems.db"
    // Create a static final int called DATABASE_VERSION and set it to 1. Will be incremented on db changes.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsitems.db";

    // For logging
    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_NEWSITEM_TABLE = "CREATE TABLE " + Contract.NewsItem.TABLE_NAME + " (" +
                Contract.NewsItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.NewsItem.COLUMN_SOURCE + " TEXT NOT NULL, " +
                Contract.NewsItem.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                Contract.NewsItem.COLUMN_TITLE + " TEXT NOT NULL, " +
                Contract.NewsItem.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.NewsItem.COLUMN_URL + " TEXT NOT NULL, " +
                Contract.NewsItem.COLUMN_URL_TO_IMAGE + " TEXT NOT NULL, " +
                Contract.NewsItem.COLUMN_PUBLISHED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        Log.d(TAG, "Create SQL: " + SQL_CREATE_NEWSITEM_TABLE);
        db.execSQL(SQL_CREATE_NEWSITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If DATABASE_VERSION changes, drop table and create new db.
        db.execSQL("DROP TABLE IF EXISTS " + Contract.NewsItem.TABLE_NAME);
        onCreate(db);
    }
}
