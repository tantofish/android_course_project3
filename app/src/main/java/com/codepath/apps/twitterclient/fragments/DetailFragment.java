package com.codepath.apps.twitterclient.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.utils.TwitterApplication;
import com.codepath.apps.twitterclient.utils.TwitterClient;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.codepath.apps.twitterclient.utils.RelativeDateParser;
import com.codepath.apps.twitterclient.utils.RoundCornerTransformation;
import com.squareup.picasso.Picasso;

/**
 * Created by yutu on 8/12/15.
 */

public class DetailFragment extends DialogFragment {

    private EditText etMessage;
    private TextView tvLengthLeft;
    private TextView tvUsername;
    private TextView tvName;
    private ImageView ivProfileImage;
    private ImageView ivMedia;
    private TextView tvTime;
    private TextView tvRetweet;
    private TextView tvFavorate;


    private TwitterClient client;
    private Button btnPost;
    TextView tvBody;

    private Context mContext;
    private Tweet mTweet;

    public DetailFragment() {
        // Empty constructor required for DialogFragment
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setModel(Tweet tweet) {
        this.mTweet = tweet;
    }

    public static DetailFragment newInstance(Context context, Tweet tweet) {
        DetailFragment frag = new DetailFragment();
        frag.setContext(context);
        frag.setModel(tweet);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container);
        client = TwitterApplication.getRestClient();

        setupView(view);
        setViewListeners();

        // Show soft keyboard automatically
        etMessage.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    private void setupView(View view) {
        etMessage = (EditText) view.findViewById(R.id.etMessage);
        tvLengthLeft = (TextView) view.findViewById(R.id.tvLengthLeft);
        tvUsername = (TextView) view.findViewById(R.id.tvUserName);
        tvName = (TextView) view.findViewById(R.id.tvName);
        ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
        btnPost = (Button) view.findViewById(R.id.btnPost);
        tvBody = (TextView) view.findViewById(R.id.tvBody);
        ivMedia = (ImageView) view.findViewById(R.id.ivMedia);

        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvRetweet = (TextView) view.findViewById(R.id.tvRetweet);
        tvFavorate = (TextView) view.findViewById(R.id.tvFavorite);

        User user = mTweet.getUser();

        mTweet.getCreatedAt();
        mTweet.getFavoriteCount();
        mTweet.getRetweetCount();
        tvRetweet.setText(Integer.toString(mTweet.getRetweetCount()));
        tvFavorate.setText(Integer.toString(mTweet.getFavoriteCount()));
        tvTime.setText(RelativeDateParser.reFormatTime(mTweet.getCreatedAt()));

        tvUsername.setText(user.getScreenName());
        tvName.setText("@" + user.getName());
        Picasso.with(mContext)
                .load(user.getProfileImageUrl())
                .error(R.drawable.profile_error)
                .placeholder(R.drawable.placeholder_profile)
                .transform(new RoundCornerTransformation())
                .into(ivProfileImage);
        tvBody.setText(mTweet.getBody());

        if(mTweet.getMediaType().equals("photo")) {
            Picasso.with(mContext)
                    .load(mTweet.getMediaUrl())
                    .error(R.drawable.profile_error)
                    .placeholder(R.drawable.placeholder_pic)
                    .transform(new RoundCornerTransformation())
                    .into(ivMedia);
        }
    }

    private void setViewListeners() {
        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvLengthLeft.setText(Integer.toString(140-s.length()));
            }
        });
//
//        btnPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String message = etMessage.getText().toString();
//
//                ((TimelineActivity) getActivity()).postTweet(message);
//                ((TimelineActivity) getActivity()).hideTweetOverlay();
//
//            }
//        });
    }
}