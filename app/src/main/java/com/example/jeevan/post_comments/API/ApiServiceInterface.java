package com.example.jeevan.post_comments.API;

import com.example.jeevan.post_comments.model.Post;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by jeevan on 3/14/16.
 */
public interface ApiServiceInterface {
    @GET("/post/index")
    void getPosts(Callback<List<Post>> response);
}
