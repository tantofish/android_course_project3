package com.codepath.apps.twitterclient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.activities.TimelineActivity;
import com.codepath.apps.twitterclient.interfaces.EndlessScrollListener;
import com.codepath.apps.twitterclient.utils.TwitterApplication;
import com.codepath.apps.twitterclient.utils.TwitterClient;
import com.codepath.apps.twitterclient.adapters.TweetsArrayAdapter;
import com.codepath.apps.twitterclient.models.Tweet;

import java.util.ArrayList;

/**
 * Created by yutu on 8/20/15.
 */
public class TweetsListFragment extends Fragment {

    // member attributes
    protected ArrayList<Tweet> tweets;
    protected TweetsArrayAdapter aTweets;
    protected ListView lvTweets;
    protected SwipeRefreshLayout swipeContainer;
    protected TwitterClient client;
    protected boolean isDataEnd;
    // inflation logic
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        setupViews(v);
        return v;
    }

    private void setupViews(View v) {
        // Pull to Refresh
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Custom View Adapter setting pattern
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
    }


    // creation life cycle
    // this is executed before onCreateView
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
        // Get Rest Client
        client = TwitterApplication.getRestClient();
    }

    public void rePopulateTimeline(int count, int since) {
        this.isDataEnd = false;
        populateTimeline(count, since, true);
    }

    public void populateTimeline(int count, int since) {
        populateTimeline(count, since, false);
    }

    public void populateTimeline(int count, int since, final boolean clear){
        // need to be overrided in the child fragments
    }

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

                ((TimelineActivity) getActivity()).showDetailOverlay(tweets.get(position));
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rePopulateTimeline(25, 1);
            }
        });
    }

    public void add(int index, Tweet tweet) {
        tweets.add(index, tweet);
        aTweets.notifyDataSetChanged();
    }
//    public Tweet get(int index) { return tweets.get(index); }
//    public void stopRefreshing() { swipeContainer.setRefreshing(false); }
//    public void addAll(List<Tweet> tweets) { aTweets.addAll(tweets); }
//    public void clear() { aTweets.clear(); }
//    public ListView getLvTweets() { return lvTweets; }
//    public SwipeRefreshLayout getSwipeContainer() { return swipeContainer; }

}
