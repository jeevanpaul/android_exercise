package com.example.jeevan.post_comments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeevan.post_comments.API.ApiServiceInterface;
import com.example.jeevan.post_comments.model.Post;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    ListView listView;
    public List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPosts();
    }

    public void postForm(View view) {
        Intent intent = new Intent(this, PostFormActivity.class);
        startActivity(intent);
    }

    public void getPosts() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://192.168.1.25/android_backend").setLogLevel(RestAdapter.LogLevel.FULL).build();
        ApiServiceInterface api = adapter.create(ApiServiceInterface.class);
        api.getPosts(new Callback<List<Post>>() {
            @Override
            public void success(List<Post> list, Response response) {
                //Storing the data in our list
                posts = list;
                listView = (ListView) findViewById(R.id.list);
                listView.setOnItemClickListener(MainActivity.this);
                //Calling a method to show the list
                showList();
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
            }
        });
    }

    public void showList() {
        ArrayAdapter<Post> adapter = new ArrayAdapter<Post>(this, android.R.layout.simple_list_item_1, posts);
        listView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Creating an intent
        Intent intent = new Intent(this, PostViewActivity.class);

        //Getting the requested post from the list
        Post post = posts.get(position);

        //Adding book details to intent
        intent.putExtra("id", post.id);
        //Starting another activity to show post details
        startActivity(intent);
    }
}
