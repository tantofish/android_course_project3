package com.codepath.apps.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by yutu on 8/12/15.
 */
public class User implements Serializable{
    // list the attributes
    protected long uid;
    protected long id;
    protected String name;
    protected String screenName;
    protected String profileImageUrl;
    protected String profileBannerUrl;
    protected int followersCount;
    protected int friendsCount;
    protected String location;
    protected String display_url;


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

    // deserialize the JSON
    public static User fromJSON(JSONObject json) {
        User u = new User();

        try {
            u.profileBannerUrl = json.getString("profile_banner_url");
        } catch (JSONException e) {
            u.profileBannerUrl = null;
        }

        try {
            u.display_url = json.getJSONObject("entities").getJSONObject("url").getJSONArray("urls").getJSONObject(0).getString("display_url");
        } catch (JSONException e) {
            u.display_url = null;
        }

        try {
            u.id = json.getLong("id");
            u.name = json.getString("name");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.followersCount = json.getInt("followers_count");
            u.friendsCount = json.getInt("friends_count");
            u.location = json.getString("location");

            //Log.d("DEBUG", "uid: " + u.uid + "uname: " + u.name + "usName: " + u.screenName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }
}
