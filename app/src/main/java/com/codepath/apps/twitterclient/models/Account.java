package com.codepath.apps.twitterclient.models;

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
            //Log.d("DEBUG", "Account--> id: " + account.id + " name: " + account.name + " sName: " + account.screenName + " piURL: " + account.profileImageUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return account;
    }
}
