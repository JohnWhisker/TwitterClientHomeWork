package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.codepath.apps.restclienttemplate.Adapter.TweetArrayAdapter;
import com.codepath.apps.restclienttemplate.Listeners.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.Others.Tweet;
import com.codepath.apps.restclienttemplate.Others.TwitterApplication;
import com.codepath.apps.restclienttemplate.Others.TwitterClient;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

/**
 * Created by johnw on 4/3/2016.
 */
public class HomeTimelineFragment extends TweetsListFragment {
    public static ArrayList<Tweet> tweets;
    public static TweetArrayAdapter aTweets;
    @Bind(R.id.rvTweets)
    RecyclerView recyclerView;
    @Bind(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    private TwitterClient client;
    private boolean clear;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        tweets = new ArrayList<>();
        aTweets = new TweetArrayAdapter(tweets);
        clear = false;
        recyclerView.setItemAnimator(new SlideInRightAnimator());
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeContainer.setRefreshing(false);
                        clear = true;
                        populateTimeLIne(0);
                    }
                }, 3000);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                populateTimeLIne(page);
            }

        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(aTweets);
        client = TwitterApplication.getRestClient();
        populateTimeLIne(1);
    }

    private void populateTimeLIne(int page) {

        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (clear) {
                    tweets.clear();
                    aTweets.notifyDataSetChanged();
                }
                tweets.addAll(Tweet.fromJSONArray(response));
                aTweets.setList(tweets);
                aTweets.notifyDataSetChanged();
                if (clear) {
                    clear = false;
                    swipeContainer.setRefreshing(clear);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }, page);

    }


}
