package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.TweetItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Komal on 2/21/2016.
 */
// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class TweetsAdapter extends
        RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    private static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView name;
        public TextView userName;
        public TextView tweetText;
        public ImageView tweetImage;
        public ImageView profileImage;
        public TextView ts;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            userName = (TextView) itemView.findViewById(R.id.userName);
            tweetText = (TextView) itemView.findViewById(R.id.tweetText);
            tweetImage = (ImageView) itemView.findViewById(R.id.tweetImage);
            ts = (TextView) itemView.findViewById(R.id.timeStamp);
        }
    }

    // Store a member variable for the contacts
    private List<TweetItem>  mTweets;
    private Context context;

    // Pass in the contact array into the constructor
    public TweetsAdapter(List<TweetItem> tweets, Context context) {
        mTweets = tweets;
        this.context = context;
    }


    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public TweetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tweetView = inflater.inflate(R.layout.tweet_row, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TweetsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
       TweetItem tweet = mTweets.get(position);

        // Set item views based on the data model
        TextView name = viewHolder.name;
        name.setText(tweet.getName());

        TextView userName = viewHolder.userName;
        userName.setText(tweet.getUserName());

        TextView tweetText = viewHolder.tweetText;
        tweetText.setText(tweet.getBody());

        TextView ts = viewHolder.ts;
        ts.setText(getRelativeTimeAgo( tweet.getTimeStamp()));

        ImageView img = viewHolder.tweetImage;
        if (tweet.getImageUrl()!=null) {
            Picasso.with(context).load(tweet.getImageUrl()).into(img);
        }



    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return mTweets.size();
    }

}