package com.codepath.apps.restclienttemplate;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by johnw on 3/27/2016.
 */
public class TweetArrayAdapter extends RecyclerView.Adapter<TweetArrayAdapter.ViewHolder> {
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

    @Override
    public Tweet onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Tweet holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
