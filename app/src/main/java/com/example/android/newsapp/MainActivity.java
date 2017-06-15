package com.example.android.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.newsapp.utilities.NetworkUtils;

import org.w3c.dom.Text;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mNewsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsTextView = (TextView) findViewById(R.id.news_data);

        loadNewsData();
    }

    private void loadNewsData() {
        new FetchNewsTask().execute();
    }

    public class FetchNewsTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            URL newsRequestURL = NetworkUtils.buildUrl();

            try {
                return NetworkUtils.getResponseFromHttpUrl(newsRequestURL);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String newsData) {
            if (newsData != null) {
                mNewsTextView.setText(newsData);
            }
        }
    }
}
