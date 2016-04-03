package com.codepath.apps.restclienttemplate.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.Tweet;
import com.codepath.apps.restclienttemplate.TweetArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class TweetsListFragment extends Fragment {
    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter aTweets;

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    public TweetArrayAdapter getaTweets() {
        return aTweets;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweet_list,container,false);
        tweets = new ArrayList<>();
        aTweets = new TweetArrayAdapter(tweets);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    public void tclear(){
        tweets.clear();
        aTweets.notifyDataSetChanged();
    }
    public void addAll(List<Tweet> tweet){
        tweets.addAll(tweet);
    }


}
