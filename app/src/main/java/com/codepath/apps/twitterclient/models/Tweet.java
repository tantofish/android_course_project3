package com.codepath.apps.twitterclient.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/* Created by yutu on 8/12/15.

    [
         {

         },
         {
            ....
         }
    ]

*/

// Parse the JSON + Store the data, encapsulate state logic or display logic
public class Tweet {
    // list attributes
    private String body;
    private String createdAt;
    private int retweetCount;
    private int favoriteCount;
    private boolean retweeted;
    private long uid;   // unique id for the tweet
    private User user;

    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    // Deserialize the JSON
    // Tweet.fromJSON("{ ... }") => <Tweet>
    public static Tweet fromJSON(JSONObject jObj) {
        Tweet tweet = new Tweet();


        try {
            tweet.retweetCount = jObj.getInt("retweet_count");
            tweet.favoriteCount = jObj.getInt("favorite_count");
            tweet.retweeted = jObj.getBoolean("retweeted");
            tweet.uid  = jObj.getLong("id");
            tweet.body = jObj.getString("text");
            tweet.createdAt = jObj.getString("created_at");
            tweet.user = User.fromJSON(jObj.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Return tweet Object
        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jarr) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i = 0 ; i < jarr.length() ; i++) {
            try {
                JSONObject jo = jarr.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(jo);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }
}
