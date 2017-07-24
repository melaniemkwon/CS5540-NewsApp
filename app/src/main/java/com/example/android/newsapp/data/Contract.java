package com.example.android.newsapp.data;

import android.provider.BaseColumns;

/**
 * Created by melaniekwon on 7/23/17.
 */

// HW4: 3. Create inner class Contract class that implements BaseColumns interface
public class Contract {

    public static final class NewsItem implements BaseColumns {

        // HW4: 3. Create a static final members for the table name and each of the db columns
        public static final String TABLE_NAME = "news_items";

        public static final String COLUMN_SOURCE = "source";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URL_TO_IMAGE = "urlToImage";
        public static final String COLUMN_PUBLISHED_AT = "published_at";
    }

}
