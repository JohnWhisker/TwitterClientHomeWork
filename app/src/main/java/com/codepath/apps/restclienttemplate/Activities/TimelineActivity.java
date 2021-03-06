package com.codepath.apps.restclienttemplate.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.Others.TwitterApplication;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.MentionTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.PostTweet;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimelineActivity extends AppCompatActivity {
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tablayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView mTitle;
    private PostTweet PostDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Twitter");
        getSupportActionBar().setLogo(getResources().getDrawable(R.drawable.ic_logo));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blue));
        TweetsPagerAdapter adapter = new TweetsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeTimelineFragment());
        adapter.addFragment(new MentionTimelineFragment());
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        PostDialog = new PostTweet(TwitterApplication.getRestClient());
        tablayout.setupWithViewPager(viewPager);
    }

    public void doThis(View view) {
        PostDialog.show(getFragmentManager(), "fragment_dialog");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hometimeline_menu, menu);
        return true;
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = {"Home", "Mentions"};
        private ArrayList<Fragment> listFragment = new ArrayList<>();

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        public void addFragment(Fragment fragment) {
            if (!listFragment.contains(fragment))
                listFragment.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

}


