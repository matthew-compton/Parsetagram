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
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mBottomNavigationView.setSelectedItemId(R.id.bottom_nav_feed);
        NavigationUtils.navigate(LandingActivity.this, R.id.fragment_container, FeedFragment.newInstance());
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
                NavigationUtils.navigate(LandingActivity.this, containerResId, FeedFragment.newInstance());
                break;
            case R.id.bottom_nav_camera:
                NavigationUtils.navigate(LandingActivity.this, containerResId, CameraFragment.newInstance());
                break;
            case R.id.bottom_nav_profile:
                NavigationUtils.navigate(LandingActivity.this, containerResId, ProfileFragment.newInstance());
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
