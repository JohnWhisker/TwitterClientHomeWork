package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        tweets = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvTweets);
        aTweets = new TweetArrayAdapter(tweets);
        recyclerView.setAdapter(aTweets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                populateTimeLIne(page);
            }
        });
        client = TwitterApplication.getRestClient();
        populateTimeLIne(1);
    }
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    TweetArrayAdapter  aTweets;

    private void populateTimeLIne(int page) {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                tweets.addAll(Tweet.fromJSONArray(response));
                aTweets.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        },page);

    }
}
