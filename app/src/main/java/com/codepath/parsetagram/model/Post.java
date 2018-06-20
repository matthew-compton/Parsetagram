package com.codepath.parsetagram.model;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {

    private static final int QUERY_LIMIT = 20;
    private static final String KEY_AUTHOR = "KEY_AUTHOR";
    private static final String KEY_MEDIA = "KEY_MEDIA";
    private static final String KEY_CAPTION = "KEY_CAPTION";
    private static final String KEY_CREATED_AT = "createdAt";

    public ParseUser getAuthor() {
        return getParseUser(KEY_AUTHOR);
    }

    public void setAuthor(ParseUser author) {
        put(KEY_AUTHOR, author);
    }

    public ParseFile getMedia() {
        return getParseFile(KEY_MEDIA);
    }

    public void setMedia(ParseFile media) {
        put(KEY_MEDIA, media);
    }

    public String getCaption() {
        return getString(KEY_CAPTION);
    }

    public void setCaption(String caption) {
        put(KEY_CAPTION, caption);
    }

    public static Post newInstance(ParseUser author, ParseFile media, String caption) {
        Post post = new Post();
        post.setAuthor(author);
        post.setMedia(media);
        post.setCaption(caption);
        post.saveInBackground();
        return post;
    }

    public static void getPostsForCurrentUser(FindCallback<Post> callback) {
        getPostsForUser(callback, ParseUser.getCurrentUser());
    }

    public static void getPostsForUser(FindCallback<Post> callback, ParseUser user) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.setLimit(QUERY_LIMIT);
        query.whereEqualTo(KEY_AUTHOR, user);
        query.include(KEY_AUTHOR);
        query.addDescendingOrder(KEY_CREATED_AT);
        query.findInBackground(callback);
    }

    public static void getPost(FindCallback<Post> callback, Date date) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.setLimit(1);
        query.whereEqualTo(KEY_CREATED_AT, date);
        query.include(KEY_AUTHOR);
        query.findInBackground(callback);
    }
}