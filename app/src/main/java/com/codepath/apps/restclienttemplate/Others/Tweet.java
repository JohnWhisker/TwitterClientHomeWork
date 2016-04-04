package com.codepath.apps.restclienttemplate.Others;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by johnw on 3/26/2016.
 */
public class Tweet {
    private String body;
    private long uid;
    private User user;
    private String createAt,mediaUrl,mediaType;

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getBody() {
        return body;
    }

    public User getUser() {
        return user;
    }

    public long getUid() {
        return uid;
    }

    public String getCreateAt() {
        return createAt;
    }


    public static Tweet fromJSON (JSONObject jsonObject){
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createAt = jsonObject.getString("created_at");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
            try{
                 tweet.mediaUrl = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url");
                 tweet.mediaType = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("type");
            }catch (JSONException f){ f.printStackTrace();
                tweet.mediaUrl = null;tweet.mediaType = null;}
        }catch (JSONException e){
            e.printStackTrace();
        }
        return tweet;
    }
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet!=null){
                    tweets.add(tweet);
                }
            }catch (JSONException e){
                e.printStackTrace();
                continue;
            }

        }

        return tweets;
    }
}
