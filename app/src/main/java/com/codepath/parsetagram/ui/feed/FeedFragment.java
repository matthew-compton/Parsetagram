package com.codepath.parsetagram.ui.feed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.parsetagram.R;
import com.codepath.parsetagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

public class FeedFragment extends Fragment {

    private static final String TAG = FeedFragment.class.toString();

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    public FeedFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.swiperefresh) protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rvPosts) protected RecyclerView mRecyclerView;

    private FeedCallbacks mListener;
    private FeedAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private OnRefreshListener mOnRefreshListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        initUI();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FeedCallbacks) {
            mListener = (FeedCallbacks) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*
     * UI Updates
     */

    private void initUI() {
        if (mAdapter == null) {
            mAdapter = new FeedAdapter();
        }
        if (mOnRefreshListener == null) {
            mOnRefreshListener = new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mSwipeRefreshLayout.setRefreshing(true);
                    Post.getPostsForCurrentUser(new FindCallback<Post>() {
                        public void done(List<Post> posts, ParseException e) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            if (e != null) {
                                Log.w(TAG, "Error: " + e.getMessage());
                                return;
                            }
                            mAdapter.setItems(posts);
                            mLayoutManager.scrollToPosition(0);
                        }
                    });
                }
            };
        }
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mOnRefreshListener.onRefresh();
    }

}
