package com.example.jeevan.post_comments;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    public ListView listView;
    public List<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.postview_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

        postid = intent.getIntExtra("id", 0);
        getPostDetails();
    }

    public void getPostDetails() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://192.168.1.8/android_backend").setLogLevel(RestAdapter.LogLevel.FULL).build();
        ApiServiceInterface api = adapter.create(ApiServiceInterface.class);
        api.showPost(postid, new Callback<Post>() {
            @Override
            public void success(Post list, Response response) {
                TextView textViewcontent1 = (TextView) findViewById(R.id.contenttext);
                textViewcontent1.setText("Content : ");
                TextView textViewauthor1 = (TextView) findViewById(R.id.titletext);
                textViewauthor1.setText("Title : ");
                TextView textViewtitle1 = (TextView) findViewById(R.id.authortext);
                textViewtitle1.setText("Author : ");
                TextView textViewcontent = (TextView) findViewById(R.id.content);
                textViewcontent.setText(list.content);
                TextView textViewtitle = (TextView) findViewById(R.id.title);
                textViewtitle.setText(list.title);
                TextView textViewauthor = (TextView) findViewById(R.id.author);
                textViewauthor.setText(list.author);
                getComments(postid);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
            }

        });
    }

    public void getComments(int id) {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://192.168.1.8/android_backend").setLogLevel(RestAdapter.LogLevel.FULL).build();
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(PostViewActivity.this, CommentViewActivity.class);
                Comment comment = comments.get(position);
                intent.putExtra("id", comment.id);
                intent.putExtra("postid", postid);
                startActivity(intent);
            }
        });
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
