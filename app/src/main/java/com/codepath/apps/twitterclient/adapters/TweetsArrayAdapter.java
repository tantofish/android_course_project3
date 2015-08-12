package com.codepath.apps.twitterclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.utils.RelativeDateParser;
import com.codepath.apps.twitterclient.utils.RoundCornerTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yutu on 8/12/15.
 * Taking the Tweet objects and turning them into displayed in listview
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{

    // constructor
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, R.layout.item_tweet, tweets);
    }

    // Override and setup custom template
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1 get the tweet
        Tweet tweet = getItem(position);
        // 2 find or inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        // 3 find the subview to fill with data in the template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        TextView tvRetweet = (TextView) convertView.findViewById(R.id.tvRetweet);
        TextView tvFavorate = (TextView) convertView.findViewById(R.id.tvFavorite);


        // 4 populate data into the subview
        tvUserName.setText(tweet.getUser().getScreenName());
        tvName.setText("@"+tweet.getUser().getName());
        tvTime.setText(RelativeDateParser.getRelativeTime(tweet.getCreatedAt()));
        tvBody.setText(tweet.getBody());
        tvRetweet.setText(Integer.toString(tweet.getRetweetCount()));
        tvFavorate.setText(Integer.toString(tweet.getFavoriteCount()));

        ivProfileImage.setImageResource(android.R.color.transparent);   // clear out the old image for a recycled view
        Picasso.with(getContext())
                .load(tweet.getUser().getProfileImageUrl())
                .error(R.drawable.profile_error)
                .placeholder(R.drawable.placeholder_profile)
                .transform(new RoundCornerTransformation())
                .into(ivProfileImage);
        // 5 return the view to be inserted into the list

        return convertView;
    }
}
