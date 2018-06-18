package com.codepath.parsetagram;

import android.app.Application;

import com.codepath.parsetagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParsetagramApplication extends Application {

    public static final String APPLICATION_ID = "parsetagram-compton";
    public static final String SERVER = "https://parsetagram-compton.herokuapp.com/parse";

    @Override
    public void onCreate() {
        super.onCreate();
        setupParse();
    }

    private void setupParse() {
        ParseObject.registerSubclass(Post.class);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(APPLICATION_ID)
                .clientBuilder(builder)
                .server(SERVER)
                .build()
        );
    }

}
