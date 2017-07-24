package com.example.android.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.android.newsapp.utilities.NewsAdapter;

// HW3: 2. Implement LoaderManager.LoaderCallbacks<Void> on MainActivity
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.ItemClickListener{
    static final String TAG = "mainactivity";

    private RecyclerView mRecyclerView;
    private EditText mSearchBoxEditText;
    private ProgressBar mLoadingIndicator;

    // HW3: 2. Create constant int to uniquely identify loader
    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadNewsData();
    }

    private void loadNewsData() {
        new FetchNewsTask("").execute();
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
                // TODO: remove search box?
                String s = mSearchBoxEditText.getText().toString();
                FetchNewsTask task = new FetchNewsTask(s);
                task.execute();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // HW3: 2. Implement methods onCreateLoader, onLoadFinished, and onLoaderReset
    //         for LoaderManager.LoaderCallbacks<Void>
    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {

        // HW3: 2. Return new AsyncTaskLoader<Void> as anonymous inner class with this as constructor's parameter
        return new AsyncTaskLoader<Void>(this) {

            // HW3: 2. Show loading indicator on the start of loading
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                // TODO: Refresh articles method
//                RefreshTasks.refreshArticles(MainActivity.this);

//                ArrayList<NewsItem> result = null;
//                URL newsRequestURL = NetworkUtils.buildUrl();
//
//                try {
//                    String json = NetworkUtils.getResponseFromHttpUrl(newsRequestURL);
//                    result = NetworkUtils.parseJSON(json);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return result;

                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        // HW3: 2. When loading is finished, hide the loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);

        // TODO: get info from db
//        db = new DBHelper(MainActivity.this).getReadableDatabase();
//        cursor = DatabaseUtils.getAll(db);

        // TODO: Reset data in recyclerview
//        adapter = new MyAdapter(cursor, this);
//        rv.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {}

    @Override
    public void onListItemClick(int clickedItemIndex) {
//        ArrayList<NewsItem> data;
//        openWebPage(data.get(clickedItemIndex).getUrl());
    }

    public void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // HW3: 2. Remove class FetchNewsTask with the AsyncTask
//    class FetchNewsTask extends AsyncTask<URL, Void, ArrayList<NewsItem>> implements NewsAdapter.ItemClickListener {
//        String query;
//        ArrayList<NewsItem> data;
//
//        FetchNewsTask(String s) {
//            query = s;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mLoadingIndicator.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected ArrayList<NewsItem> doInBackground(URL... params) {
//            ArrayList<NewsItem> result = null;
//            URL newsRequestURL = NetworkUtils.buildUrl();
//
//            try {
//                String json = NetworkUtils.getResponseFromHttpUrl(newsRequestURL);
//                result = NetworkUtils.parseJSON(json);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(final ArrayList<NewsItem> newsData) {
//            this.data = newsData;
//            super.onPostExecute(data);
//            mLoadingIndicator.setVisibility(View.INVISIBLE);
//
//            if (data != null) {
//                NewsAdapter adapter = new NewsAdapter(data, this);
//                mRecyclerView.setAdapter(adapter);
//            }
//        }
//
//        @Override
//        public void onListItemClick(int clickedItemIndex) {
//            openWebPage(data.get(clickedItemIndex).getUrl());
//        }
//
//        public void openWebPage(String url) {
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                startActivity(intent);
//            }
//        }
//    }
}
