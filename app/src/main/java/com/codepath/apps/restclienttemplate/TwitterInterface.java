package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.ProfileData;
import com.codepath.apps.restclienttemplate.models.TweetItem;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Komal on 2/27/2016.
 */
public class TwitterInterface {

    public static ProfileData getProfile(final Context context, String type, String screenName, final notifier nfy) {

        final ProfileData ret = new ProfileData();

        AsyncHttpResponseHandler handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here


                Toast toast = Toast.makeText(context, "Fetched User" + " tweets", Toast.LENGTH_SHORT);
                toast.show();
                try {
                    String name = response.getString("name");
                    String screenName = response.getString("screen_name");
                    Integer followers = response.getInt("followers_count");
                    Integer tweets = response.getInt("statuses_count");
                    Integer following = response.getInt("friends_count");
                    String picUrl = response.getString("profile_image_url");
                    String description = response.getString("description");

                    ProfileData data = new ProfileData(name, screenName, picUrl, description, followers, following, tweets);
                    ret.copy(data);
                    nfy.onLoadSuccess(data);


                } catch (JSONException e) {
                    e.printStackTrace();
                    nfy.onLoadFailure();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject resp) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                Log.d("kartik failure ", resp.toString());
                nfy.onLoadFailure();

                Toast toast = Toast.makeText(context, "Failure " + resp.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        };

        if (type.equals("self")) {
            RestApplication.getRestClient().getMyUserInfo(handler);
        } else {
            RestApplication.getRestClient().getUserInfo(handler, screenName);
        }

        return ret;
    }

    public static void getTweets(final String type, final String screen_name, final boolean append, int page, final List<TweetItem> tList, final Context context, final TweetsAdapter adapter) {

        long since_id = 0;
        long max_id = 0;
        TweetItem newestItem = null;
        if (tList.size() > 0) newestItem = tList.get(0);
        TweetItem oldestItem = null;
        if (tList.size() > 0) oldestItem = tList.get(tList.size() - 1);
        if (newestItem != null) {
            since_id = newestItem.getId();
        }
        if ((oldestItem != null) && append) {
            max_id = oldestItem.getId();
        }

        AsyncHttpResponseHandler handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here

                ArrayList<TweetItem> list = new ArrayList<TweetItem>();

                Toast toast = Toast.makeText(context, "Fetched " + screen_name + "'s " + response.length() + " tweets", Toast.LENGTH_SHORT);
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

                Toast toast = Toast.makeText(context, "Failure " + resp.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        };


        if (type.equals("mentions")) {
            RestApplication.getRestClient().getMentionsTimeline(since_id, max_id, handler);
        } else {
            if (screen_name.equals("self")) {
                RestApplication.getRestClient().getHomeTimeline(since_id, max_id, handler);
            } else {
                RestApplication.getRestClient().getUserTimeline(screen_name, since_id, max_id, handler);
            }
        }

    }

    public interface notifier {
        void onLoadSuccess(ProfileData prof);

        void onLoadFailure();
    }
}
