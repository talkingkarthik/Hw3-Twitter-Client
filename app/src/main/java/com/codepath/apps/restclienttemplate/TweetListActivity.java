package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.restclienttemplate.models.TweetItem;

import java.util.List;

public class TweetListActivity extends AppCompatActivity
        implements TimeLineFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener, SampleFragmentPagerAdapter.fragmentManagerCom {

    RecyclerView rvTweets;
    List<TweetItem> tList;
    TweetsAdapter adapter;
    String screen_name;
    SampleFragmentPagerAdapter pageAdapter;
    ViewPager viewPager;
    LinearLayoutManager lm;

    public String getScreenName() {
        return screen_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_list);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pageAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pageAdapter);

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

        switchUser("self");
    }

    public void switchUser(String screen_name) {
        this.screen_name = screen_name;
        refreshProfileFragment();
        viewPager.setCurrentItem(2);
        viewPager.setCurrentItem(0);

    }

    public void refreshProfileFragment() {

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.your_placeholder, ProfileFragment.newInstance(getScreenName(), getScreenName()));
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
