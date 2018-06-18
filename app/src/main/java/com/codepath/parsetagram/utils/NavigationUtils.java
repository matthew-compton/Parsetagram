package com.codepath.parsetagram.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class NavigationUtils {

    public static void navigateAndFinish(@NonNull Activity activity, @NonNull Intent intent) {
        navigate(activity, intent);
        activity.finish();
    }

    public static void navigate(@NonNull Context context, @NonNull Intent intent) {
        context.startActivity(intent);
    }

}
