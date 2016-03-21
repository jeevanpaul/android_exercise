package com.example.jeevan.post_comments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jeevan.post_comments.API.ApiServiceInterface;
import com.example.jeevan.post_comments.model.Post;
import com.example.jeevan.post_comments.model.Comment;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jeevan on 3/17/16.
 */
public class CommentViewActivity extends AppCompatActivity {
    public int commentid;
    public int postid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_view);
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.commentview_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        commentid = intent.getIntExtra("id", 0);
        postid = intent.getIntExtra("postid", 0);
        getCommentDetails();
    }

    public void getCommentDetails() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://192.168.1.8/android_backend").setLogLevel(RestAdapter.LogLevel.FULL).build();
        ApiServiceInterface api = adapter.create(ApiServiceInterface.class);
        api.showComment(this.commentid, new Callback<Comment>() {
            @Override
            public void success(Comment list, Response response) {
                TextView textViewcontent1 = (TextView) findViewById(R.id.commentcontenttext);
                textViewcontent1.setText("Content : ");
                TextView textViewtitle1 = (TextView) findViewById(R.id.commentauthortext);
                textViewtitle1.setText("Author : ");
                TextView textViewcontent = (TextView) findViewById(R.id.commentcontent);
                textViewcontent.setText(list.content);
                TextView textViewauthor = (TextView) findViewById(R.id.commentauthor);
                textViewauthor.setText(list.author);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
            }
        });
    }

    public void postView(View view) {
        Intent returnintent = new Intent(this, PostViewActivity.class);
        returnintent.putExtra("id", this.postid);
        startActivity(returnintent);
    }

    public void home(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                home1();
                break;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void home1() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }
}
