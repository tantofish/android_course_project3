package com.codepath.apps.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.fragments.DetailFragment;
import com.codepath.apps.twitterclient.fragments.UserTimelineFragment;
import com.codepath.apps.twitterclient.models.Account;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.codepath.apps.twitterclient.utils.RoundCornerTransformationEx;
import com.squareup.picasso.Picasso;


public class ProfileActitivity extends ActionBarActivity {

    ImageView ivBanner;
    ImageView ivProfile;
    TextView tvUserName;
    TextView tvName;
    TextView tvLocation;
    TextView tvDisplayUrl;
    TextView tvFriendsCount;
    TextView tvFollowersCount;

    User account;
    User user;
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        Intent i = this.getIntent();
        account = (User) i.getSerializableExtra("user");

        if (account == null) {
            account = (User) Account.getInstance();
        }



        setupViews();
        if (savedInstanceState == null) {
            UserTimelineFragment fgUserTimeline = (UserTimelineFragment) UserTimelineFragment.newInstance(account.getScreenName());
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.flContainer, fgUserTimeline);
            ft.commit();
        }

        getSupportActionBar().hide();
    }

    private void setupViews() {
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        ivBanner = (ImageView) findViewById(R.id.ivBanner);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvName = (TextView) findViewById(R.id.tvName);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvDisplayUrl = (TextView) findViewById(R.id.tvDisplayUrl);
        tvFriendsCount =  (TextView) findViewById(R.id.tvFriendsCount);
        tvFollowersCount =  (TextView) findViewById(R.id.tvFollowersCount);

        if (account.getProfileImageUrl() != null) {
            Picasso.with(getBaseContext())
                    .load(account.getProfileImageUrl())
                    .error(R.drawable.profile_error)
                    .placeholder(R.drawable.ic_profile_clicked)
                    .transform(new RoundCornerTransformationEx())
                    .into(ivProfile);
        }
        if (account.getProfileBannerUrl() != null) {
            Picasso.with(getBaseContext())
                    .load(account.getProfileBannerUrl())
                    .error(R.drawable.profile_error)
                    .placeholder(R.drawable.placeholder_pic)
                    .into(ivBanner);
        }
        tvLocation.setText(account.getLocation());
        tvUserName.setText(account.getName());
        tvName.setText(account.getScreenName());
        tvDisplayUrl.setText(account.getDisplay_url());
        tvFriendsCount.setText(Integer.toString(account.getFriendsCount()));
        tvFollowersCount.setText(Integer.toString(account.getFollowersCount()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_actitivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDetailOverlay(Tweet tweet) {
        FragmentManager fm = getSupportFragmentManager();
        detailFragment = DetailFragment.newInstance(getBaseContext(), tweet);
        detailFragment.show(fm, "fragment_detail");
    }
}
