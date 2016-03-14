package com.example.jeevan.post_comments.model;

/**
 * Created by jeevan on 3/14/16.
 */
public class Post {
    public int id;
    public String title;
    public String content;
    public String author;

    @Override
    public String toString() {
        return this.title;
    }
}
