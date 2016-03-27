package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by johnw on 3/27/2016.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetArrayAdapter (Context context, List<Tweet> tweets){
        super(context,android.R.layout.simple_list_item_1,tweets);
    }
}
