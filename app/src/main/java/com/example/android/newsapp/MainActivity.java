package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.TextView;

import com.example.android.newsapp.model.NewsItem;
import com.example.android.newsapp.utilities.NetworkUtils;
import com.example.android.newsapp.utilities.NewsAdapter;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

// 8. Implement NewsAdapter.ItemClickListener from the MainActivity
public class MainActivity extends AppCompatActivity {
    static final String TAG = "mainactivity";

    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private EditText mSearchBoxEditText;
    private ProgressBar mLoadingIndicator;

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

        // 13. Pass in this as the ItemClickListener to the NewsAdapter constructor
//        mNewsAdapter = new NewsAdapter()
//        mRecyclerView.setAdapter(mNewsAdapter);
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
                String s = mSearchBoxEditText.getText().toString();
                FetchNewsTask task = new FetchNewsTask(s);
                task.execute();
                return true;
//            case R.id.
        }

        return super.onOptionsItemSelected(item);
    }

    class FetchNewsTask extends AsyncTask<URL, Void, ArrayList<NewsItem>> implements NewsAdapter.ItemClickListener {
        String query;
        ArrayList<NewsItem> data;

        FetchNewsTask(String s) {
            query = s;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(URL... params) {
            ArrayList<NewsItem> result = null;
            URL newsRequestURL = NetworkUtils.buildUrl();

            try {
                String json = NetworkUtils.getResponseFromHttpUrl(newsRequestURL);
                result = NetworkUtils.parseJSON(json);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItem> newsData) {
            this.data = newsData;
            super.onPostExecute(data);
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (data != null) {
                // 13. Pass in this as the ItemClickListener to the NewsAdapter constructor
                NewsAdapter adapter = new NewsAdapter(data, this);
                mRecyclerView.setAdapter(adapter);
            }
        }

        // 10. Override ItemClickListener's onListItemClick method
        @Override
        public void onListItemClick(int clickedItemIndex) {
            // 11. Open url link in browser
            openWebPage(data.get(clickedItemIndex).getUrl());
        }

        public void openWebPage(String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
