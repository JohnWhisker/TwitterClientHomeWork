package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.Others.Tweet;
import com.codepath.apps.restclienttemplate.Adapter.TweetArrayAdapter;
import com.codepath.apps.restclienttemplate.Others.TwitterApplication;
import com.codepath.apps.restclienttemplate.Others.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by johnw on 4/3/2016.
 */
public class MentionTimelineFragment extends TweetsListFragment {
    @Bind(R.id.rvTweets)
    RecyclerView recyclerView;
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter aTweets;
    private boolean clear;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(aTweets);
        client = TwitterApplication.getRestClient();
        populateTimeLIne(1);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        aTweets = new TweetArrayAdapter(tweets);
        clear = false;
    }

    private void populateTimeLIne(int page) {
        client.getMentionTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (clear) {
                    tweets.clear();
                    aTweets.notifyDataSetChanged();
                }
                tweets.addAll(Tweet.fromJSONArray(response));
                aTweets.notifyDataSetChanged();
                if (clear) {
                    clear = false;
                    //   swipeContainer.setRefreshing(clear);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }, page);
    }
}
