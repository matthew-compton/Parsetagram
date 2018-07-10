package com.codepath.parsetagram.ui.landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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

    @BindView(R.id.viewpager) protected ViewPager mViewPager;
    @BindView(R.id.bottom_navigation) protected BottomNavigationView mBottomNavigationView;

    private FeedFragment mFeedFragment;
    private CameraFragment mCameraFragment;
    private ProfileFragment mProfileFragment;
    private PagerAdapter mPagerAdapter;

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
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    default:
                    case 0:
                        return mFeedFragment;
                    case 1:
                        return mCameraFragment;
                    case 2:
                        return mProfileFragment;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mBottomNavigationView.setSelectedItemId(R.id.bottom_nav_feed);
    }


    /*
     * UI Listeners
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            default:
            case R.id.bottom_nav_feed:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.bottom_nav_camera:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.bottom_nav_profile:
                mViewPager.setCurrentItem(2);
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
