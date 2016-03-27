package com.codepath.apps.restclienttemplate;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by johnw on 3/27/2016.
 */
public class User {
    private String name,screenName,profileImageUrl;
    private long uid;

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public long getUid() {
        return uid;
    }

    public static User fromJson(JSONObject jsonObject){
        User user = new User();
    try {
         user.name = jsonObject.getString("name");
         user.uid = jsonObject.getLong("id");
         user.screenName = jsonObject.getString("screen_name");
         user.profileImageUrl = jsonObject.getString("profile_image_url");
        } catch (JSONException e){e.printStackTrace();}

        return user ;
    }

}
