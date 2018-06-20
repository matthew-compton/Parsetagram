package com.codepath.parsetagram.ui.landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.codepath.parsetagram.R;
import com.codepath.parsetagram.ui.camera.CameraCallbacks;
import com.codepath.parsetagram.ui.camera.CameraFragment;
import com.codepath.parsetagram.ui.feed.FeedCallbacks;
import com.codepath.parsetagram.ui.feed.FeedFragment;
import com.codepath.parsetagram.ui.login.LoginActivity;
import com.codepath.parsetagram.ui.profile.ProfileCallbacks;
import com.codepath.parsetagram.ui.profile.ProfileFragment;
import com.codepath.parsetagram.utils.AuthenticationUtils;
import com.codepath.parsetagram.utils.NavigationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity implements
        FeedCallbacks,
        CameraCallbacks,
        ProfileCallbacks,
        BottomNavigationView.OnNavigationItemSelectedListener {

    public static Intent newIntent(Context context) {
        return new Intent(context, LandingActivity.class);
    }

    @BindView(R.id.fragment_container) protected FrameLayout mContainer;
    @BindView(R.id.bottom_navigation) protected BottomNavigationView mBottomNavigationView;

    private FeedFragment mFeedFragment;
    private CameraFragment mCameraFragment;
    private ProfileFragment mProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        initUI();
    }

    /*
     * UI Updates
     */

    private void initUI() {
        ButterKnife.bind(this);
        if (mFeedFragment == null) {
            mFeedFragment = FeedFragment.newInstance();
        }
        if (mCameraFragment == null) {
            mCameraFragment = CameraFragment.newInstance();
        }
        if (mProfileFragment == null) {
            mProfileFragment = ProfileFragment.newInstance();
        }
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mBottomNavigationView.setSelectedItemId(R.id.bottom_nav_feed);
    }

    /*
     * UI Listeners
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int containerResId = R.id.fragment_container;
        switch (item.getItemId()) {
            default:
            case R.id.bottom_nav_feed:
                NavigationUtils.navigate(this, containerResId, mFeedFragment);
                break;
            case R.id.bottom_nav_camera:
                NavigationUtils.navigate(this, containerResId, mCameraFragment);
                break;
            case R.id.bottom_nav_profile:
                NavigationUtils.navigate(this, containerResId, mProfileFragment);
                break;
        }
        return true;
    }

    @Override
    public void onClickLogout() {
        AuthenticationUtils.logout();
        NavigationUtils.navigateAndFinish(this, LoginActivity.newIntent(this));
    }

}
