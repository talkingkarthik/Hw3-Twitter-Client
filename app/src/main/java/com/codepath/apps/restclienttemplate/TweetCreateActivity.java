package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class TweetCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_create);


        Button tweetBtn = (Button) findViewById(R.id.button);
        tweetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicker();
            }
        });
    }

    private void tweet (String text) {
        RestApplication.getRestClient().postTweet(text, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast toast = Toast.makeText(getApplicationContext(), "Success " , Toast.LENGTH_SHORT);
                toast.show();

                showTweets();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject resp) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                Log.d("kartik failure ", resp.toString());

                Toast toast = Toast.makeText(getApplicationContext(), "Failure " + resp.toString(), Toast.LENGTH_SHORT);
                toast.show();

                showTweets();
            }
        });
    }

    private void onClicker () {
        EditText composer = (EditText) findViewById(R.id.etComposeTweet);
        tweet(composer.getText().toString());
    }


    private void showTweets () {
        Intent intent = new Intent(this, TweetListActivity.class);
        startActivity(intent);
    }
}
