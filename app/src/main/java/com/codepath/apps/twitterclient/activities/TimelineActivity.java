package com.codepath.apps.twitterclient.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.TwitterApplication;
import com.codepath.apps.twitterclient.TwitterClient;
import com.codepath.apps.twitterclient.adapters.TweetsArrayAdapter;
import com.codepath.apps.twitterclient.fragments.PostTweetFragment;
import com.codepath.apps.twitterclient.interfaces.EndlessScrollListener;
import com.codepath.apps.twitterclient.models.Account;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends ActionBarActivity {
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    PostTweetFragment tweetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this, tweets);

        lvTweets.setAdapter(aTweets);
        client = TwitterApplication.getRestClient();

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline(25, totalItemsCount);
            }
        });
        getAccountInfo();
        populateTimeline(25, 1);
    }

    private void getAccountInfo() {
        client.getAccountCredentials(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Account.fromJSON(json);
                Log.d("DEBUG", json.toString());
                Log.d("DEBUG", "Send getAccountCredentials to API on SUCCESS!!!");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject err) {
                Log.d("DEBUG", "Send getAccountCredentials to API on FAILED!!!" + err.toString());
            }
        });
    }
    // Send API
    // Fill listview
    private void populateTimeline(int count, int since) {
        populateTimeline(count, since, false);
    }

    public void populateTimeline(int count, int since, final boolean clear){
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                // JSON
                // 1. Deserialize json
                // 2. Create Models
                // 3. Load the Model into ListView (adapter needed)
                if (clear) {
                    aTweets.clear();
                }
                aTweets.addAll(Tweet.fromJSONArray(json));

                Log.d("DEBUG", json.toString());
                Log.d("DEBUG", "Send getHomeTimeline to API on SUCCESS!!!");
                //Tweet.fromJSON(json);
            }

            // Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject err) {
                Log.d("DEBUG", "Send getHomeTimeline to API on FAILED!!!" + err.toString());
            }
        }, count, since);
    }

    public void postTweet(String message) {
        client.postTweet(new JsonHttpResponseHandler() {
            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Log.d("DEBUG", json.toString());
                Log.d("DEBUG", "Send postTweet to API on SUCCESS!!!");
                Tweet mTweet = Tweet.fromJSON(json);
                tweets.add(0, mTweet);
                aTweets.notifyDataSetChanged();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.post_tweet) {
            showTweetOverlay();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addTweet(Tweet t){
        tweets.add(0, t);
        aTweets.notifyDataSetChanged();
    }

    private void showTweetOverlay() {
        FragmentManager fm = getSupportFragmentManager();
        tweetFragment = PostTweetFragment.newInstance(this);
        tweetFragment.show(fm, "fragment_post_tweet");
    }

    public void hideTweetOverlay() {
        tweetFragment.dismiss();
    }

}
