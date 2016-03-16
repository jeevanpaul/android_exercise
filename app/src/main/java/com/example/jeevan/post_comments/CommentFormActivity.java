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

public class CommentFormActivity extends AppCompatActivity {
    public String author;
    public String content;
    public int post_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_form);
        Intent intent = getIntent();
        this.post_id = intent.getIntExtra("post_id", 0);
    }
    public void index(View view) {
        Intent intent = new Intent(this, PostViewActivity.class);

        EditText authortext = (EditText) findViewById(R.id.comment_author);
        this.author = authortext.getText().toString();
        EditText contenttext = (EditText) findViewById(R.id.comment_content);
        this.content = contenttext.getText().toString();
        intent.putExtra("comment_author", this.author);
        intent.putExtra("comment_content", this.content);
        intent.putExtra("id", this.post_id);
        saveComment();
        startActivity(intent);
    }

    public void saveComment() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://192.168.1.25/android_backend").setLogLevel(RestAdapter.LogLevel.FULL).build();
        ApiServiceInterface api = adapter.create(ApiServiceInterface.class);
        api.saveComment(this.content, this.author, this.post_id, new Callback<List<Empty>>() {
            @Override
            public void success(List<Empty> list, Response response) {
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
