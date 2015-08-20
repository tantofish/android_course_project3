package com.codepath.apps.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.fragments.DetailFragment;
import com.codepath.apps.twitterclient.fragments.HomeTimelineFragment;
import com.codepath.apps.twitterclient.fragments.MentionsTimelineFragment;
import com.codepath.apps.twitterclient.fragments.PostTweetFragment;
import com.codepath.apps.twitterclient.models.Account;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.utils.TwitterApplication;
import com.codepath.apps.twitterclient.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class TimelineActivity extends ActionBarActivity {

    private PostTweetFragment tweetFragment;
    private DetailFragment detailFragment;
    private HomeTimelineFragment homeTimelineFragment;
    private MentionsTimelineFragment mentionsTimelineFragment;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // Post Tweet Fragment
        tweetFragment = PostTweetFragment.newInstance(this);

        // access fragment
        if (savedInstanceState == null) {
            //homeTimelineFragment = (HomeTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
            //mentionsTimelineFragment = (MentionsTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
        }

        //setupListeners();
        client = TwitterApplication.getRestClient();

        getAccountInfo();


        // get the viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        // set the viewpager adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        // find the pager sliding tabs
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // attach the pager tabs to the viewpager
        tabStrip.setViewPager(vpPager);
    }

    private void getAccountInfo() {
        client.getAccountCredentials(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Account.fromJSON(json);
                Log.d("DEBUG", "Send getAccountCredentials to API on SUCCESS!!!");
                Log.d("DEBUG", json.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject err) {
                Log.d("DEBUG", "Send getAccountCredentials to API on FAILED!!!");
                Log.d("DEBUG", err.toString());
            }
        });
    }

    public void postTweet(String message) {
        client.postTweet(new JsonHttpResponseHandler() {
            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Log.d("DEBUG", json.toString());
                Log.d("DEBUG", "Send postTweet to API on SUCCESS!!!");
                Tweet mTweet = Tweet.fromJSON(json);
                homeTimelineFragment.add(0, mTweet);
                Toast.makeText(getApplicationContext(), "Post Tweet Succeed", Toast.LENGTH_SHORT).show();
            }

            // Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject err) {
                Log.d("DEBUG", "Send postTweet to API on FAILED!!!" + err.toString());
                Toast.makeText(getApplicationContext(), "Post Tweet Failed", Toast.LENGTH_SHORT).show();
            }
        }, message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = {"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else if(position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
    //public void addTweet(Tweet t){
    //    tweetsListFragment.add(0, t);
    //}

    public void onProfileView(MenuItem mi) {
        Log.d("DEBUG", "onProfileView");
        Intent i = new Intent(this, ProfileActitivity.class);
        startActivity(i);
    }

    public void showDetailOverlay(Tweet tweet) {
        FragmentManager fm = getSupportFragmentManager();
        detailFragment = DetailFragment.newInstance(getBaseContext(), tweet);
        detailFragment.show(fm, "fragment_detail");
    }

    public void onPostTweet(MenuItem mi) {
        FragmentManager fm = getSupportFragmentManager();
        tweetFragment.show(fm, "fragment_post_tweet");
    }

    public void hideTweetOverlay() {
        tweetFragment.dismiss();
    }

}
