package com.codepath.apps.restclienttemplate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {
    private Toolbar toolbartop;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        tweets = new ArrayList<>();
        toolbartop = (Toolbar) findViewById(R.id.toolbartop);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvTweets);
        aTweets = new TweetArrayAdapter(tweets);
        recyclerView.setAdapter(aTweets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
       toolbartop.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               Toast.makeText(TimelineActivity.this,"You just long click, son", Toast.LENGTH_SHORT).show();
               showInputDialog();
           
               return true;
           }
       });
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
        @Override
            public void onLoadMore(int page, int totalItemsCount) {

                populateTimeLIne(page);
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            int verticalOffset;

            // Determines the scroll UP/DOWN direction
            boolean scrollingUp;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (scrollingUp) {
                        if (verticalOffset > toolbartop.getHeight()) {
                            toolbarAnimateHide();
                        } else {
                            toolbarAnimateShow(verticalOffset);
                        }
                    } else {
                        if (toolbartop.getTranslationY() < toolbartop.getHeight() * -0.6 && verticalOffset > toolbartop.getHeight()) {
                            toolbarAnimateHide();
                        } else {
                            toolbarAnimateShow(verticalOffset);
                        }
                    }
                }
            }


            @Override
            public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                verticalOffset += dy;
                scrollingUp = dy > 0;
                int toolbarYOffset = (int) (dy - toolbartop.getTranslationY());
                toolbartop.animate().cancel();
                if (scrollingUp) {
                    if (toolbarYOffset < toolbartop.getHeight()) {
                        if (verticalOffset > toolbartop.getHeight()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                toolbarSetElevation(toolbartop.getElevation());
                            }
                        }
                        toolbartop.setTranslationY(-toolbarYOffset);
                    } else {
                        toolbarSetElevation(0);
                        toolbartop.setTranslationY(-toolbartop.getHeight());
                    }
                } else {
                    if (toolbarYOffset < 0) {
                        if (verticalOffset <= 0) {
                            toolbarSetElevation(0);
                        }
                        toolbartop.setTranslationY(0);
                    } else {
                        if (verticalOffset > toolbartop.getHeight()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                toolbarSetElevation(toolbartop.getElevation());
                            }
                        }
                        toolbartop.setTranslationY(-toolbarYOffset);
                    }
                }
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
                Log.d("DEBUG2", tweets.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        },page);

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void toolbarSetElevation(float elevation) {
        // setElevation() only works on Lollipop

            toolbartop.setElevation(elevation);

    }

    private void toolbarAnimateShow(final int verticalOffset) {
        toolbartop.animate()
                .translationY(0)
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            toolbarSetElevation(verticalOffset == 0 ? 0 : toolbartop.getElevation());
                        }
                    }
                });
    }

    private void toolbarAnimateHide() {
       toolbartop.animate()
                .translationY(-toolbartop.getHeight())
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toolbarSetElevation(0);
                    }
                });
    }



    protected void showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(TimelineActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_status, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TimelineActivity.this);
        alertDialogBuilder.setView(promptView);
        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       Toast.makeText(TimelineActivity.this,"Tweet is: "+editText.getText().toString(),Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        Toast.makeText(TimelineActivity.this,editText.getText(),Toast.LENGTH_SHORT).show();
    }

}
