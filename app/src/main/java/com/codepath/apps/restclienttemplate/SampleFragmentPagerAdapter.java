package com.codepath.apps.restclienttemplate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    fragmentManagerCom com;
    private String tabTitles[] = new String[]{"Timeline", "Mentions", "Profile"};

    public SampleFragmentPagerAdapter(FragmentManager fm, fragmentManagerCom com) {
        super(fm);
        this.com = com;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position + 1) {
            case 1:
                return TimeLineFragment.newInstance("timeline", com.getScreenName());

            case 2:
                return TimeLineFragment.newInstance("mentions", com.getScreenName());

            case 3:
                return ProfileFragment.newInstance("self", "self");

            default:
                return TimeLineFragment.newInstance("timeline", com.getScreenName());
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    public interface fragmentManagerCom {
        String getScreenName();

        void switchUser(String screenName);
    }
}