package com.example.jeevan.post_comments.model;

/**
 * Created by jeevan on 3/16/16.
 */
public class Comment {
    public int id;
    public String status;
    public String author;
    public String content;

    @Override
    public String toString() {
        return this.content;
    }
}
