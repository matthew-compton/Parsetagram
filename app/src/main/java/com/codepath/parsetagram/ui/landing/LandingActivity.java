package com.codepath.parsetagram.ui.landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.codepath.parsetagram.R;
import com.codepath.parsetagram.ui.login.LoginActivity;
import com.codepath.parsetagram.utils.AuthenticationUtils;
import com.codepath.parsetagram.utils.NavigationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity {

    private static final String TAG = LandingActivity.class.toString();

    public static Intent newIntent(Context context) {
        return new Intent(context, LandingActivity.class);
    }

    @BindView(R.id.container) protected FrameLayout mContainer;
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
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            default:
                            case R.id.bottom_nav_feed:
                                break;
                            case R.id.bottom_nav_camera:
                                break;
                            case R.id.bottom_nav_profile:
                                break;
                        }
                        return true;
                    }
                });
    }

    /*
     * UI Listeners
     */

    public void onClickLogout(View view) {
        AuthenticationUtils.logout();
        navigateToLogin();
    }

    /*
     * Navigation
     */

    private void navigateToLogin() {
        Intent intent = LoginActivity.newIntent(this);
        NavigationUtils.navigateAndFinish(this, intent);
    }

}
