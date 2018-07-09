package com.codepath.parsetagram.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.parsetagram.R;
import com.codepath.parsetagram.model.Post;
import com.codepath.parsetagram.utils.DateUtils;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static String EXTRA_CREATED_AT = "EXTRA_CREATED_AT";

    public static Intent newIntent(Context context, Post post) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_CREATED_AT, post.getCreatedAt());
        return intent;
    }

    @BindView(R.id.ivMedia) protected ImageView ivPicture;
    @BindView(R.id.tvCaption) protected TextView tvCaption;
    @BindView(R.id.tvTimestamp) protected TextView tvTimestamp;

    private Post mPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setupToolbar();
        load();
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void load() {
        Date date = (Date) getIntent().getSerializableExtra(EXTRA_CREATED_AT);
        Post.getPost(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (posts == null || posts.isEmpty()) {
                    return;
                }
                mPost = posts.get(0);
                initUI();
            }
        }, date);
    }

    private void initUI() {
        String caption = mPost.getCaption();
        String url = mPost.getMedia().getUrl();
        String timestamp = DateUtils.getFormattedTime(mPost.getCreatedAt());
        tvCaption.setText(caption);
        tvTimestamp.setText(timestamp);
        Glide.with(this)
                .load(url)
                .into(ivPicture);
    }

}
