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
import com.codepath.apps.twitterclient.activities.TimelineActivity;
import com.codepath.apps.twitterclient.models.Account;
import com.codepath.apps.twitterclient.utils.RoundCornerTransformation;
import com.squareup.picasso.Picasso;

/**
 * Created by yutu on 8/12/15.
 */

public class PostTweetFragment extends DialogFragment {

    private EditText etMessage;
    private TextView tvLengthLeft;
    private TextView tvUsername;
    private TextView tvName;
    private ImageView ivProfileImage;
    private TwitterClient client;
    private Button btnPost;


    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    private Context mContext;

    public PostTweetFragment() {
        // Empty constructor required for DialogFragment
    }

    public static PostTweetFragment newInstance(Context context) {
        PostTweetFragment frag = new PostTweetFragment();
        frag.setContext(context);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_tweet, container);
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
        Account account = Account.getInstance();

        tvUsername.setText(account.getScreenName());
        tvName.setText("@" + account.getName());
        Picasso.with(mContext)
                .load(account.getProfileImageUrl())
                .error(R.drawable.profile_error)
                .placeholder(R.drawable.placeholder_profile)
                .transform(new RoundCornerTransformation())
                .into(ivProfileImage);

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

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString();

                ((TimelineActivity) getActivity()).postTweet(message);
                ((TimelineActivity) getActivity()).hideTweetOverlay();

                etMessage.setText("");
            }
        });
    }
}