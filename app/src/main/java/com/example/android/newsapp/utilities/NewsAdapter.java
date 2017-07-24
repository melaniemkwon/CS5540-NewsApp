package com.example.android.newsapp.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.newsapp.R;
import com.example.android.newsapp.data.NewsItem;

import java.util.ArrayList;

/**
 * Created by melaniekwon on 6/25/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private static final String TAG = NewsAdapter.class.getSimpleName();

    private ArrayList<NewsItem> data;

    // 3. Create final private ItemClickListener
    final private ItemClickListener listener;

    public NewsAdapter(ArrayList<NewsItem> data, ItemClickListener listener) {
        this.data = data;
        // 4. Add ListItemClickListener as parameter to constructor
        this.listener = listener;
    }

    // 1. Add interface
    public interface ItemClickListener {
        // 2. define void method that takes an int parameter
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParent = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, attachToParent);

        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // 5. Implement OnClickListener in NewsAdapterViewHolder class
    class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mNewsTitle;
        public final TextView mNewsDescription;
        public final TextView mNewsTime;

        public NewsAdapterViewHolder(View itemView) {
            super(itemView);
            mNewsTitle = (TextView) itemView.findViewById(R.id.news_title);
            mNewsDescription = (TextView) itemView.findViewById(R.id.news_description);
            mNewsTime = (TextView) itemView.findViewById(R.id.news_time);
            // 7. Call setOnClickListener on the View passed into the constructor
            itemView.setOnClickListener(this);
        }

        public void bind(int pos) {
            NewsItem newsItem = data.get(pos);
            mNewsTitle.setText(newsItem.getTitle());
            mNewsDescription.setText(newsItem.getDescription());
            mNewsTime.setText(newsItem.getPublishedAt());
        }

        // 6. Override onClick, passing in clicked item's pos
        @Override
        public void onClick(View v) {
            listener.onListItemClick(getAdapterPosition());
        }
    }

    public void setData(ArrayList<NewsItem> data) {
        this.data = data;
    }
}
