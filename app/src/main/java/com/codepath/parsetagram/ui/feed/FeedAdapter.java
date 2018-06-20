package com.codepath.parsetagram.ui.feed;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.parsetagram.R;
import com.codepath.parsetagram.model.Post;
import com.codepath.parsetagram.ui.details.DetailActivity;
import com.codepath.parsetagram.utils.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<Post> mPosts;

    public FeedAdapter() {
        mPosts = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        final Post post = mPosts.get(position);
        String caption = post.getCaption();
        String url = post.getMedia().getUrl();
        holder.tvCaption.setText(caption);
        Glide.with(context)
                .load(url)
                .bitmapTransform(new RoundedCornersTransformation(context, 8, 0))
                .into(holder.ivPicture);
        holder.ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = DetailActivity.newIntent(context, post);
                NavigationUtils.navigate(context, intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public void setItems(List<Post> posts) {
        int diff = posts.size() - mPosts.size();
        mPosts = posts;
        if (diff != 0) {
            notifyItemRangeInserted(0, diff);
        } else {
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivMedia) protected ImageView ivPicture;
        @BindView(R.id.tvCaption) protected TextView tvCaption;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
