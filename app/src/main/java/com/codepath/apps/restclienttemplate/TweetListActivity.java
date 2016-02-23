package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.TweetItem;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TweetListActivity extends AppCompatActivity {

    RecyclerView rvTweets;
    List<TweetItem> tList;
    TweetsAdapter adapter;

    LinearLayoutManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_list);


        // Lookup the recyclerview in activity layout
        rvTweets = (RecyclerView) findViewById(R.id.rvTweets);

        tList = new ArrayList<TweetItem>();

        // Create adapter passing in the sample user data
        adapter = new TweetsAdapter(tList, this);

        // Attach the adapter to the recyclerview to populate items
        rvTweets.setAdapter(adapter);
        // Set layout manager to position the items

        lm = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(lm);
        // That's all!

        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(lm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                Log.d("kartik", "onLoadMore called");

                getTweets(true, page);
            }
        });


        getTweets(true, 0);

    }

    public void composeTweet(MenuItem mi) {
        Intent intent = new Intent(this, TweetCreateActivity.class);
        startActivity (intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.composebar, menu);
        return true;
    }

    private void getTweets (final boolean append, int page) {

        long since_id =0;
        long max_id =0;
        TweetItem newestItem = null; if (tList.size() > 0) newestItem = tList.get (0);
        TweetItem oldestItem = null; if (tList.size() > 0) oldestItem = tList.get (tList.size() -1);
        if (newestItem != null) {
            since_id = newestItem.getId();
        } if ((oldestItem != null) && append) {
            max_id = oldestItem.getId();
        }


        RestApplication.getRestClient().getHomeTimeline(since_id, max_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here

                ArrayList<TweetItem> list = new ArrayList<TweetItem>();

                Toast toast = Toast.makeText(getApplicationContext(), "Fetched " + response.length() + " tweets", Toast.LENGTH_SHORT);
                toast.show();
                try {

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject tweetJson = response.getJSONObject(i);
                        JSONObject user = tweetJson.getJSONObject("user");
                        String text = tweetJson.getString("text");
                        String name = user.getString("name");
                        String userName = user.getString("screen_name");
                        String createdAt = tweetJson.getString("created_at");
                        String profileImageUrl = user.getString("profile_image_url");
                        long id = tweetJson.getLong("id");

                        TweetItem item = new TweetItem(id, userName, profileImageUrl, createdAt, profileImageUrl, text, name);
                        list.add(item);

                    }

                    if (append) {
                        tList.addAll(list);
                        adapter.notifyDataSetChanged();
                    } else {
                        tList.clear();
                        tList.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject resp) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                Log.d("kartik failure ", resp.toString());

                Toast toast = Toast.makeText(getApplicationContext(), "Failure " + resp.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }



}
