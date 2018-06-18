package com.codepath.parsetagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_USER = "KEY_USER";
    public static final String KEY_MEDIA = "KEY_MEDIA";
    public static final String KEY_CAPTION = "KEY_CAPTION";
    public static final String KEY_AUTHOR = "KEY_AUTHOR";
    public static final String KEY_COUNT_LIKES = "KEY_COUNT_LIKES";
    public static final String KEY_COUNT_COMMENTS = "KEY_COUNT_COMMENTS";

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setParseUser(ParseUser user) {
        put(KEY_USER, user);
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

    public String getAuthor() {
        return getString(KEY_AUTHOR);
    }

    public void setAuthor(String author) {
        put(KEY_AUTHOR, author);
    }

    public int getCountLikes() {
        return getInt(KEY_COUNT_LIKES);
    }

    public void setCountLikes(int countLikes) {
        put(KEY_COUNT_LIKES, countLikes);
    }

    public int getCountComments() {
        return getInt(KEY_COUNT_COMMENTS);
    }

    public void setCountComments(int countComments) {
        put(KEY_COUNT_COMMENTS, countComments);
    }

}