package com.codepath.apps.restclienttemplate.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.Adapter.TweetArrayAdapter;
import com.codepath.apps.restclienttemplate.Others.Tweet;
import com.codepath.apps.restclienttemplate.Others.TwitterClient;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by johnw on 4/4/2016.
 */
@SuppressLint("ValidFragment")
public class PostTweet extends DialogFragment {
    ArrayList<Tweet> tweets;
    TweetArrayAdapter adapter;
    String body;
    private TwitterClient client;
    private EditText etComposeTweet;

    @SuppressLint("ValidFragment")
    public PostTweet(TwitterClient client) {
        this.client = client;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.post_tweet, null);
        tweets = new ArrayList<>();
        adapter = new TweetArrayAdapter(tweets);
        AlertDialog.Builder TweetDialog = new AlertDialog.Builder(getActivity());
        TweetDialog.setTitle("Post Tweet");
        TweetDialog.setView(dialogView)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etComposeTweet = (EditText) dialogView.findViewById(R.id.posttxt);
                        int length = etComposeTweet.getText().length();
                        body = etComposeTweet.getText().toString();
                        client.postTweet(body, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                                Tweet myNewTweet = Tweet.fromJSON(json);
                                HomeTimelineFragment.tweets.add(0, myNewTweet);
                                HomeTimelineFragment.aTweets.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                Log.d("Madness", errorResponse.toString());
                            }

                        });

                    }

                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        return TweetDialog.create();

    }
}
