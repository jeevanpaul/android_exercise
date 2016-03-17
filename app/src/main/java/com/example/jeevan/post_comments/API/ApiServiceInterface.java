package com.example.jeevan.post_comments.API;

import com.example.jeevan.post_comments.model.Empty;
import com.example.jeevan.post_comments.model.Post;
import com.google.gson.JsonObject;

import com.example.jeevan.post_comments.model.Comment;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by jeevan on 3/14/16.
 */
public interface ApiServiceInterface {
    @GET("/post/index")
    void getPosts(Callback<List<Post>> callback);
    @GET("/comment/index")
    void getComments(@Query("id")int id, Callback<List<Comment>> callback);
    @GET("/post/create")
    void savePost(@Query("content")String content,@Query("author")String author,@Query("title")String title, Callback<Empty> callback);
    @GET("/comment/create")
    void saveComment(@Query("content")String content,@Query("author")String author,@Query("post_id")int post_id, Callback<Empty> callback);
    @GET("/post/show")
    void showPost(@Query("postid")int postid,Callback<Post> callback );
    @GET("/comment/show")
    void showComment(@Query("commentid")int commentid,Callback<Comment> callback );
}
