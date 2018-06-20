package com.codepath.parsetagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_AUTHOR = "KEY_AUTHOR";
    public static final String KEY_MEDIA = "KEY_MEDIA";
    public static final String KEY_CAPTION = "KEY_CAPTION";

    public static Post newInstance(ParseUser author, ParseFile media, String caption) {
        Post post = new Post();
        post.setAuthor(author);
        post.setMedia(media);
        post.setCaption(caption);
        post.saveInBackground();
        return post;
    }

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

}