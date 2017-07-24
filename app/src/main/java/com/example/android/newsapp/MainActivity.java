package com.example.android.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.android.newsapp.data.Contract;
import com.example.android.newsapp.data.DBHelper;
import com.example.android.newsapp.data.DBUtils;
import com.example.android.newsapp.utilities.NewsAdapter;
import com.example.android.newsapp.utilities.NewsJob;
import com.example.android.newsapp.utilities.ScheduleUtils;

// HW4: 2. Implement LoaderManager.LoaderCallbacks<Void> on MainActivity
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.ItemClickListener{
    static final String TAG = "mainactivity";

    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private EditText mSearchBoxEditText;
    private ProgressBar mLoadingIndicator;

    // HW4: 4. Create local field member of type SQLiteDatabase and Cursor object
    private SQLiteDatabase mDb;
    private Cursor cursor;

    // HW4: 2. Create constant int to uniquely identify loader
    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // HW4: 7. Have activity laod what's currently in database into recyclerview for display.
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // HW4: 6. Check if app has been installed before. If not, load data into db.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);

        if (isFirst) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        ScheduleUtils.scheduleRefresh(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // HW4: 4. Get a writable database reference and store in mDb
        mDb = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DBUtils.getAll(mDb);
        mNewsAdapter = new NewsAdapter(cursor, this);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

        switch (itemNumber) {
            case R.id.search:
                LoaderManager loaderManager = getSupportLoaderManager();
                loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // HW4: 2. Implement methods onCreateLoader, onLoadFinished, and onLoaderReset
    //         for LoaderManager.LoaderCallbacks<Void>
    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {

        // HW4: 2. Return new AsyncTaskLoader<Void> as anonymous inner class with this as constructor's parameter
        return new AsyncTaskLoader<Void>(this) {

            // HW4: 2. Show loading indicator on the start of loading
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                // HW4: 7. Refresh articles method
                NewsJob.refreshArticles(MainActivity.this);
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        // HW4: 2. When loading is finished, hide the loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);

        // HW4: 7. get info from db
        mDb = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DBUtils.getAll(mDb);

        // HW4: 7. Reset data in recyclerview
        mNewsAdapter = new NewsAdapter(cursor, this);
        mRecyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {}

    // HW4: 4. Update onListItemClick to include Cursor
    @Override
    public void onListItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.NewsItem.COLUMN_URL));
        Log.d(TAG, String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // HW4: 2. Remove class FetchNewsTask with the AsyncTask
}
