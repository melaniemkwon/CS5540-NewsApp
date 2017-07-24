package com.example.android.newsapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.example.android.newsapp.data.Contract.NewsItem.*;

/**
 * Created by melaniekwon on 7/24/17.
 */

// HW4: 3. Utility class to access, insert, and delete from db.
public class DBUtils {
    // Get all news articles from database
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_PUBLISHED_AT + " DESC"
        );
        return cursor;
    }

    // Insert api response to database
    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> newsItems) {

        db.beginTransaction();
        try {
            for (NewsItem a : newsItems) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_SOURCE, a.getSource());
                cv.put(COLUMN_AUTHOR, a.getAuthor());
                cv.put(COLUMN_TITLE, a.getTitle());
                cv.put(COLUMN_DESCRIPTION, a.getDescription());
                cv.put(COLUMN_URL, a.getUrl());
                cv.put(COLUMN_URL_TO_IMAGE, a.getUrlToImage());
                cv.put(COLUMN_PUBLISHED_AT, a.getPublishedAt());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }
}