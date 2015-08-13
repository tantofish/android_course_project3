package com.codepath.apps.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by yutu on 8/12/15.
 */
public class User{
    // list the attributes
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    // deserialize the JSON
    public static User fromJSON(JSONObject json) {
        User u = new User();

        try {
            u.uid = json.getLong("id");
            u.name = json.getString("name");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            //Log.d("DEBUG", "uid: " + u.uid + "uname: " + u.name + "usName: " + u.screenName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }
}
