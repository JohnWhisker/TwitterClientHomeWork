package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by johnw on 3/27/2016.
 */

public class TweetArrayAdapter extends
        RecyclerView.Adapter<TweetArrayAdapter.ViewHolder> {
    Context context;
    View tweetView;
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName,tvDate,tvBodyText;
        private ImageView ivPhoto,ivProfilePicture;
        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvBodyText = (TextView) view.findViewById(R.id.tvBodyText);
            ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
            ivProfilePicture = (ImageView) view.findViewById(R.id.ivProfilePicture);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }
    }

    private List<Tweet> mTweets;
    public TweetArrayAdapter(List<Tweet> tweets){mTweets = tweets;}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        tweetView = inflater.inflate(R.layout.tweet_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }
    public static String getTwitterDate(Date date){
        long milliseconds = date.getTime() - new Date().getTime();
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)) % 24);

        if (hours > 0){
            if (hours == 1)
                return "1 hour ago";
            else if (hours < 24)
                return String.valueOf(hours) + " hours ago";
            else
            {
                int days = (int)Math.ceil(hours % 24);
                if (days == 1)
                    return "1 day ago";
                else
                    return String.valueOf(days) + " days ago";
            }
        }
        else
        {
            if (minutes == 0)
                return "less than 1 minute ago";
            else if (minutes == 1)
                return "1 minute ago";
            else
                return String.valueOf(minutes) + " minutes ago";
        }
    }
    public static Date parseTwitterDate(String date) throws ParseException {
        final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
        sf.setLenient(true);
        return sf.parse(date);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        holder.tvName.setText(tweet.getUser().getName());
        String time = "";
        try {
             time = getTwitterDate(parseTwitterDate(tweet.getCreateAt()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(time.contains("-")) {
            time = time.substring(1);
        }
       holder.tvDate.setText(time);
        Picasso.with(context).load(tweet.getUser().getProfileImageUrl()).fit().into(holder.ivProfilePicture);
        if(tweet.getMediaUrl()!=null)
        Log.d("DEBUG",tweet.getMediaUrl());
        if (tweet.getMediaUrl()!=null) {
            holder.ivPhoto.setVisibility(View.VISIBLE);
            Log.d("DEBUG","It's photo  url: "+tweet.getMediaUrl());
            Picasso.with(context).load(tweet.getMediaUrl()).into(holder.ivPhoto);
      }
        if(tweet.getMediaUrl()==null){
        holder.tvBodyText.setText(tweet.getBody());}
        else {
            holder.tvBodyText.setText("");
        }
    }
    @Override
    public int getItemCount() {
        if(mTweets == null){return 0;}
        else return mTweets.size();
    }
}
