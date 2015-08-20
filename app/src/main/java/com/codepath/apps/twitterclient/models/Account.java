package com.codepath.apps.twitterclient.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yutu on 8/12/15.
 */

// Singleton
public class Account {
    private long id;
    private String name;
    private String screenName;
    private String profileImageUrl;
    private String profileBannerUrl;
    private int followersCount;
    private int friendsCount;
    private String location;
    private String display_url;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public String getLocation() {
        return location;
    }

    public String getDisplay_url() {
        return display_url;
    }

    public static Account getAccount() {
        return account;
    }

    private static Account account = new Account();
    public static Account getInstance() {
        return account;
    }

    private Account() {
    }

    public static Account fromJSON(JSONObject json) {
        try {
            account.id = json.getLong("id");
            account.name = json.getString("name");
            account.screenName = json.getString("screen_name");
            account.profileImageUrl = json.getString("profile_image_url");
            account.profileBannerUrl = json.getString("profile_banner_url");
            account.followersCount = json.getInt("followers_count");
            account.friendsCount = json.getInt("friends_count");
            account.location = json.getString("location");
            account.display_url = json.getJSONObject("entities").getJSONObject("url").getJSONArray("urls").getJSONObject(0).getString("display_url");

            Log.d("DEBUG", "Account--> id: " + account.id + " name: " + account.name + " sName: " + account.screenName + " piURL: " + account.profileImageUrl);
            Log.d("DEBUG", "followersCount: " + account.followersCount + " location: " + account.location + " friendsCount: " + account.friendsCount + " display_url: " + account.display_url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return account;
    }
}
