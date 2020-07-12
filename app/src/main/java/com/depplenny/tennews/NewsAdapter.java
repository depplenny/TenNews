package com.depplenny.tennews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Note that we specify the custom ViewHolder which gives us access to our views
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView titleTextView;
        public TextView timeTextView;
        public ImageView thumbnailImageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title);
            timeTextView =  itemView.findViewById(R.id.time);
            thumbnailImageView = itemView.findViewById(R.id.thumbnail);

            // Attach a click listener to the entire row (itemView)
            itemView.setOnClickListener(new View.OnClickListener() {
                // Handles the subview being being clicked
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        News news = mNewsList.get(position);
                        Intent intent = new Intent(context, ReaderActivity.class);
                        intent.putExtra("thumbnail",news.getThumbnail());
                        intent.putExtra("content", news.getContent());
                        context.startActivity(intent);
                    }
                }
            });

        }
    }

    private List<News> mNewsList;

    // Pass in the  ArrayList into the adapter constructor
    public NewsAdapter(List<News> newsList) {
        mNewsList = newsList;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    @NonNull
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom item layout
        View newsView = inflater.inflate(R.layout.item_news, parent, false);

        // Return a new ViewHolder instance
        ViewHolder viewHolder = new ViewHolder(context, newsView);
        return viewHolder;
    }

    // Involves populating data into the item through ViewHolder
    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        News news = mNewsList.get(position);

        // Set item views based on your views and data model
        TextView title = holder.titleTextView;
        title.setText(news.getTitle());

        TextView time = holder.timeTextView;
        String timeFormat = news.getTime().replace("T"," ").replace("Z","");
        time.setText(timeFormat);

        ImageView thumbnail = holder.thumbnailImageView;
        thumbnail.setImageBitmap(news.getBitmap());

    }



    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mNewsList.size();
    }


}