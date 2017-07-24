package com.example.android.newsapp.utilities;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.newsapp.R;
import com.example.android.newsapp.data.Contract;
import com.example.android.newsapp.data.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by melaniekwon on 6/25/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private static final String TAG = NewsAdapter.class.getSimpleName();

    final private ItemClickListener listener;
    private Context context;

    // HW3: 4. Add Cursor
    private Cursor mCursor;

    // HW3: 4. Modify constructor to accept cursor. Remove ArrayList<NewsItem> data.
    public NewsAdapter(Cursor cursor, ItemClickListener listener) {
        this.mCursor = cursor;
        this.listener = listener;
    }

    // HW3: 4. Modify ItemClickListener to accept Cursor
    public interface ItemClickListener {
        void onListItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
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

    // HW3: 4. Update the getItemCount to return the getCount of mCursor
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mNewsTitle;
        public final TextView mNewsDescription;
        public final TextView mNewsTime;

        // HW3: 8. Add image
        public final ImageView img;

        public NewsAdapterViewHolder(View itemView) {
            super(itemView);
            mNewsTitle = (TextView) itemView.findViewById(R.id.news_title);
            mNewsDescription = (TextView) itemView.findViewById(R.id.news_description);
            mNewsTime = (TextView) itemView.findViewById(R.id.news_time);
            img = (ImageView)itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        // HW3: 4.
        public void bind(int pos) {
            mCursor.moveToPosition(pos);

            mNewsTitle.setText(mCursor.getString(mCursor.getColumnIndex(Contract.NewsItem.COLUMN_TITLE)));
            mNewsDescription.setText(mCursor.getString(mCursor.getColumnIndex(Contract.NewsItem.COLUMN_DESCRIPTION)));
            mNewsTime.setText(mCursor.getString(mCursor.getColumnIndex(Contract.NewsItem.COLUMN_PUBLISHED_AT)));

            // TODO: IMAGE
            String urlToImage = mCursor.getString(mCursor.getColumnIndex(Contract.NewsItem.COLUMN_URL_TO_IMAGE));
            Log.d(TAG, urlToImage);
            if(urlToImage != null){
                Picasso.with(context)
                        .load(urlToImage)
                        .into(img);
            }
        }

        // HW3: 4. Add cursor to click listener call
        @Override
        public void onClick(View v) {
            listener.onListItemClick(mCursor, getAdapterPosition());
        }
    }
}
