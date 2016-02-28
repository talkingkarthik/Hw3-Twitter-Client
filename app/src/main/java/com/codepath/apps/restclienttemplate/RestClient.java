package com.codepath.apps.restclienttemplate;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class RestClient extends OAuthBaseClient {
	//public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	//public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	//public static final String REST_CONSUMER_KEY = "rLH4sOadLtrkbkTSHmLWPI6LV";       // Change this
	//public static final String REST_CONSUMER_SECRET = "UH7qrg7SN3V6cyTnPrLNnyKf0KKchNOcIDC9IODZytHLGB6aL8"; // Change this
	//public static final String REST_CALLBACK_URL = "oauth://codepathtweetskartik"; // Change this (here and in manifest)

    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "8ZPyABlg6MAU4Xh3vTABLhVS2";       // Change this
    public static final String REST_CONSUMER_SECRET = "2l6sBoM6dMe56gmc0CILD0fQ0xGqf4zx89we5UtWp5dC6Jkjoh"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://codepathtweetskartik"; // Change this (here and in manifest)



    public RestClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

    // RestClient.java
    public void getUserTimeline(String screen_name, long since_id, long max_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("screen_name", screen_name);
        if (max_id > 0) {
            // add to the list tweets older than the currently displayed tweets
            params.put("max_id", max_id);
        } else if (since_id > 0) {
            //} else {
            //start with the newest tweets
            params.put("since_id", since_id);
        }
        // execute the request
        getClient().get(apiUrl, params, handler);
    }

    // RestClient.java
    public void getHomeTimeline(long since_id, long max_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        if (max_id > 0) {
            // add to the list tweets older than the currently displayed tweets
            params.put("max_id", max_id);
        } else if (since_id > 0) {
        //} else {
            //start with the newest tweets
            params.put("since_id", since_id);
        }
        // execute the request
        getClient().get(apiUrl, params, handler);
    }

    // RestClient.java
    public void getMentionsTimeline(long since_id, long max_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        if (max_id > 0) {
            // add to the list tweets older than the currently displayed tweets
            params.put("max_id", max_id);
        } else if (since_id > 0) {
            //} else {
            //start with the newest tweets
            params.put("since_id", since_id);
        }
        // execute the request
        getClient().get(apiUrl, params, handler);
    }

    public void postTweet(String myTweet, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", myTweet);
        // execute the request
        getClient().post(apiUrl, params, handler);
    }

    // to get the logged-in user's account info
    public void getMyUserInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        RequestParams params = new RequestParams();
        // execute the request
        getClient().get(apiUrl, params, handler);
    }

    // to get the logged-in user's account info
    public void getUserInfo(AsyncHttpResponseHandler handler, String screenName) {
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        // execute the request
        getClient().get(apiUrl, params, handler);
    }


	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}