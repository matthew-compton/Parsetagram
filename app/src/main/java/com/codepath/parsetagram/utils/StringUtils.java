package com.codepath.parsetagram.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class StringUtils {

    private static final int MIN_LENGTH_PASSWORD = 5;

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() > MIN_LENGTH_PASSWORD;
    }

}
