package com.codepath.parsetagram.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class NavigationUtils {

    public static void navigateAndFinish(@NonNull Activity activity, @NonNull Intent intent) {
        navigate(activity, intent);
        activity.finish();
    }

    public static void navigate(@NonNull Context context, @NonNull Intent intent) {
        context.startActivity(intent);
    }

    public static void navigate(@NonNull AppCompatActivity activity, @IdRes int containerResId, @NonNull Fragment fragment) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerResId, fragment)
                .commit();
    }

}
