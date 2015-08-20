package com.codepath.apps.twitterclient.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.codepath.apps.twitterclient.activities.ProfileActitivity;
import com.codepath.apps.twitterclient.interfaces.EndlessScrollListener;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by yutu on 8/20/15.
 */
public class UserTimelineFragment extends TweetsListFragment{

    private String screenName;

    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment frag = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        setupListeners();
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenName = getArguments().getString("screen_name");
        isDataEnd = false;
        populateTimeline(25, 1);
    }

    // Dirty Code Here, fixme
    protected void setupListeners() {
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline(25, totalItemsCount);
            }
        });

        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DEBUG", "position: " + position + " id: " + id);

                ((ProfileActitivity) getActivity()).showDetailOverlay(tweets.get(position));
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rePopulateTimeline(25, 1);
            }
        });
    }

    // Send API
    // Fill listview
    @Override
    public void populateTimeline(int count, int since, final boolean clear){
        if(this.isDataEnd) {
            return;
        }

        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                Log.d("DEBUG", "Send getMentionsTimeline to API on SUCCESS!!!");
                // JSON
                // 1. Deserialize json
                // 2. Create Models
                // 3. Load the Model into ListView (adapter needed)
                if (clear) {
                    aTweets.clear();
                }
                aTweets.addAll(Tweet.fromJSONArray(json));
                swipeContainer.setRefreshing(false);
                if (tweets.size() < 25) {
                    isDataEnd = true;
                }
            }

            // Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject err) {
                Log.d("DEBUG", "Send getMentionsTimeline to API on FAILED!!!" + err.toString());
            }
        }, count, since);
    }

}
