package com.codepath.parsetagram.ui.landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.codepath.parsetagram.R;
import com.codepath.parsetagram.ui.login.LoginActivity;
import com.codepath.parsetagram.utils.AuthenticationUtils;
import com.codepath.parsetagram.utils.NavigationUtils;

public class LandingActivity extends AppCompatActivity {

    private static final String TAG = LandingActivity.class.toString();

    public static Intent newIntent(Context context) {
        return new Intent(context, LandingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
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
