package com.codepath.parsetagram.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codepath.parsetagram.R;
import com.codepath.parsetagram.ui.landing.LandingActivity;
import com.codepath.parsetagram.utils.AuthenticationUtils;
import com.codepath.parsetagram.utils.NavigationUtils;
import com.codepath.parsetagram.utils.StringUtils;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.toString();

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    private TextInputEditText mEditTextEmail;
    private TextInputLayout mTextInputLayoutEmail;
    private TextInputEditText mEditTextPassword;
    private TextInputLayout mTextInputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AuthenticationUtils.isAuthenticated()) {
            navigateToLanding();
        } else {
            initUI();
        }
    }

    /*
     * UI Updates
     */

    private void initUI() {
        setContentView(R.layout.activity_login);
        mEditTextEmail = findViewById(R.id.etEmail);
        mTextInputLayoutEmail = findViewById(R.id.tilEmail);
        mEditTextPassword = findViewById(R.id.etPassword);
        mTextInputLayoutPassword = findViewById(R.id.tilPassword);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isValidData(String email, String password) {
        boolean isValid = true;
        if (!StringUtils.isValidEmail(email)) {
            isValid = false;
            mTextInputLayoutEmail.setError(getString(R.string.error_invalid_email));
            mTextInputLayoutEmail.setErrorEnabled(true);
        } else {
            mTextInputLayoutEmail.setError(null);
            mTextInputLayoutEmail.setErrorEnabled(false);
        }
        if (!StringUtils.isValidPassword(password)) {
            isValid = false;
            mTextInputLayoutPassword.setError(getString(R.string.error_invalid_password));
            mTextInputLayoutPassword.setErrorEnabled(true);
        } else {
            mTextInputLayoutPassword.setError(null);
            mTextInputLayoutPassword.setErrorEnabled(false);
        }
        return isValid;
    }

    /*
     * UI Listeners
     */

    public void onClickLogin(View view) {
        String username = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();
        if (!isValidData(username, password)) {
            return;
        }
        LogInCallback callback = new LogInCallback() {
            public void done(ParseUser user, ParseException error) {
                if (user == null || error != null) {
                    Log.e(TAG, error.getMessage());
                    Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_LONG).show();
                } else {
                    navigateToLanding();
                }
            }
        };
        AuthenticationUtils.login(username, password, callback);
    }

    public void onClickRegister(View view) {
        String username = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();
        if (!isValidData(username, password)) {
            return;
        }
        SignUpCallback callback = new SignUpCallback() {
            public void done(ParseException error) {
                if (error != null) {
                    Log.e(TAG, error.getMessage());
                    Toast.makeText(LoginActivity.this, R.string.error_registration, Toast.LENGTH_LONG).show();
                } else {
                    navigateToLanding();
                }
            }
        };
        AuthenticationUtils.register(username, password, callback);
    }

    /*
     * Navigation
     */

    private void navigateToLanding() {
        Intent intent = LandingActivity.newIntent(this);
        NavigationUtils.navigateAndFinish(this, intent);
    }

}
