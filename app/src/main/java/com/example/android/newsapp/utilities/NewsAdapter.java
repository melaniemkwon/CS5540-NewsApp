package com.example.android.newsapp.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.newsapp.R;
import com.example.android.newsapp.model.NewsItem;

import java.util.ArrayList;

/**
 * Created by melaniekwon on 6/25/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private ArrayList<NewsItem> data;
    ItemClickListener listener;

    public NewsAdapter(ArrayList<NewsItem> data, ItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParent = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, attachToParent);
        NewsAdapterViewHolder viewHolder = new NewsAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mNewsTitle;
        public final TextView mNewsDescription;
        public final TextView mNewsTime;

        public NewsAdapterViewHolder(View itemView) {
            super(itemView);
            mNewsTitle = (TextView) itemView.findViewById(R.id.news_title);
            mNewsDescription = (TextView) itemView.findViewById(R.id.news_description);
            mNewsTime = (TextView) itemView.findViewById(R.id.news_time);
        }

        public void bind(int pos) {
            NewsItem newsItem = data.get(pos);
            mNewsTitle.setText(newsItem.getTitle());
            mNewsDescription.setText(newsItem.getDescription());
            mNewsTime.setText(newsItem.getPublishedAt());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }
    }
}
