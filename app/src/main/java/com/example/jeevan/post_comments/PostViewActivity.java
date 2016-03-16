package com.example.jeevan.post_comments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jeevan.post_comments.API.ApiServiceInterface;
import com.example.jeevan.post_comments.model.Comment;
import com.example.jeevan.post_comments.model.Post;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PostViewActivity extends AppCompatActivity {
    public int postid;
    Post post;
    ListView listView;
    public List<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        Intent intent = getIntent();

        this.postid = intent.getIntExtra("id", 0);
        getPostDetails();
        getComments(this.postid);
    }

    public void getPostDetails() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://192.168.1.25/android_backend").setLogLevel(RestAdapter.LogLevel.FULL).build();
        ApiServiceInterface api = adapter.create(ApiServiceInterface.class);
        api.showPost(this.postid, new Callback<Post>() {
            @Override
            public void success(Post list, Response response) {
                    TextView textViewcontent = (TextView) findViewById(R.id.content);
                    textViewcontent.setText(list.content);
                    TextView textViewtitle = (TextView) findViewById(R.id.title);
                    textViewtitle.setText(list.title);
                    TextView textViewauthor = (TextView) findViewById(R.id.author);
                    textViewauthor.setText(list.author);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
            }

        });
    }

    public void getComments(int id) {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://192.168.1.25/android_backend").setLogLevel(RestAdapter.LogLevel.FULL).build();
        ApiServiceInterface api = adapter.create(ApiServiceInterface.class);
        api.getComments(id, new Callback<List<Comment>>() {
            @Override
            public void success(List<Comment> list, Response response) {
                comments = list;
                listView = (ListView) findViewById(R.id.commentlist);
                showList();
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
            }
        });
    }

    public void showList() {
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this, android.R.layout.simple_list_item_1, comments);
        listView.setAdapter(adapter);
    }

    public void commentForm(View view) {
        Intent intent = new Intent(this, CommentFormActivity.class);
        intent.putExtra("post_id", this.postid);
        startActivity(intent);
    }

    public void home(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
