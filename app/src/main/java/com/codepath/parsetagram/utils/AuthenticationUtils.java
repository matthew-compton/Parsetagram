package com.codepath.parsetagram.utils;

import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class AuthenticationUtils {

    public static boolean isAuthenticated() {
        ParseUser user = ParseUser.getCurrentUser();
        return (user != null) && (user.isAuthenticated());
    }

    public static void login(String username, String password, LogInCallback callback) {
        ParseUser.logInInBackground(username, password, callback);
    }

    public static void register(String username, String password, SignUpCallback callback) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(username);
        user.signUpInBackground(callback);
    }

    public static void logout() {
        ParseUser.logOut();
    }

}
