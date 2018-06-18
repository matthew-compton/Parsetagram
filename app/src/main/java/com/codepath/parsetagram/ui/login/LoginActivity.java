package com.codepath.parsetagram.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.parsetagram.R;
import com.codepath.parsetagram.ui.landing.LandingActivity;
import com.codepath.parsetagram.utils.AuthenticationUtils;
import com.codepath.parsetagram.utils.KeyboardUtils;
import com.codepath.parsetagram.utils.NavigationUtils;
import com.codepath.parsetagram.utils.StringUtils;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.toString();

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @BindView(R.id.etEmail) protected TextInputEditText mEditTextEmail;
    @BindView(R.id.tilEmail) protected TextInputLayout mTextInputLayoutEmail;
    @BindView(R.id.etPassword) protected TextInputEditText mEditTextPassword;
    @BindView(R.id.tilPassword) protected TextInputLayout mTextInputLayoutPassword;
    @BindView(R.id.viewControls) protected View mViewControls;
    @BindView(R.id.viewProgress) protected View mViewProgress;

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
        ButterKnife.bind(this);
        mEditTextPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onClickLogin();
                    return true;
                }
                return false;
            }
        });
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

    private void showProgress() {
        mViewControls.setVisibility(View.INVISIBLE);
        mViewProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        mViewProgress.setVisibility(View.INVISIBLE);
        mViewControls.setVisibility(View.VISIBLE);
    }

    /*
     * UI Listeners
     */

    @OnClick(R.id.btnLogin)
    protected void onClickLogin() {
        KeyboardUtils.hide(LoginActivity.this);
        String username = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();
        if (!isValidData(username, password)) {
            return;
        }
        LogInCallback callback = new LogInCallback() {
            public void done(ParseUser user, ParseException error) {
                if (user == null || error != null) {
                    hideProgress();
                    Log.e(TAG, error.getMessage());
                    Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_LONG).show();
                } else {
                    navigateToLanding();
                }
            }
        };
        showProgress();
        AuthenticationUtils.login(username, password, callback);
    }

    @OnClick(R.id.btnRegister)
    protected void onClickRegister() {
        KeyboardUtils.hide(LoginActivity.this);
        String username = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();
        if (!isValidData(username, password)) {
            return;
        }
        SignUpCallback callback = new SignUpCallback() {
            public void done(ParseException error) {
                if (error != null) {
                    hideProgress();
                    Log.e(TAG, error.getMessage());
                    Toast.makeText(LoginActivity.this, R.string.error_registration, Toast.LENGTH_LONG).show();
                } else {
                    navigateToLanding();
                }
            }
        };
        showProgress();
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
