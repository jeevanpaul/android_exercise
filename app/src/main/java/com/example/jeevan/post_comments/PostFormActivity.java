package com.example.jeevan.post_comments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.jeevan.post_comments.API.ApiServiceInterface;
import com.example.jeevan.post_comments.model.Empty;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PostFormActivity extends AppCompatActivity {
    public String author;
    public String content;
    public String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_form);
    }

    public void index(View view) {

        EditText authortext = (EditText) findViewById(R.id.author);
        author = authortext.getText().toString();
        EditText titletext = (EditText) findViewById(R.id.title);
        title = titletext.getText().toString();
        EditText contenttext = (EditText) findViewById(R.id.content);
        content = contenttext.getText().toString();
        savePost();
    }

    public void savePost() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://192.168.1.8/android_backend").setLogLevel(RestAdapter.LogLevel.FULL).build();
        ApiServiceInterface api = adapter.create(ApiServiceInterface.class);
        api.savePost(this.content, this.author, this.title, new Callback<Empty>() {
            @Override
            public void success(Empty list, Response response) {
                if(list.status.equals("SUCCESS")) {
                    Intent intent = new Intent(PostFormActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

    }

    public void home(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
