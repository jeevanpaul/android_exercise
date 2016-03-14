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

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{
    ListView listView;
    public List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getPosts();
        /*application.getApiService().getRecentPosts(new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response arg1) {
                handleData(response_data_obj);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong, please try again later!", Toast.LENGTH_LONG).show();
            }
        });*/

       /* String[] values = new String[]{"1st post"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);*/

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listView.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(), "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this, PostViewActivity.class);
                String message=itemValue;
                intent.putExtra("EXTRA_MESSAGE", message);

                startActivity(intent);

            }
        });*/
    }

    public void postForm(View view) {
        Intent intent = new Intent(this, PostFormActivity.class);
        startActivity(intent);

    }

    /*public void handleData(JsonObject response_data_obj) {
        if (jsonObject.get("status").getAsString().equals("SUCCESS")) {
            posts = new Gson().fromJson(jsonObject.get("posts"), new TypeToken<List<Post>>() {
            }.getType());
            listView = (ListView) findViewById(R.id.list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, posts);
            listView.setAdapter(adapter);
        }
    }*/

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
        ArrayAdapter<Post> adapter = new ArrayAdapter<Post>(this, android.R.layout.simple_list_item_1,posts);
        listView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Creating an intent
        Intent intent = new Intent(this, PostViewActivity.class);

        //Getting the requested post from the list
        Post post = posts.get(position);

        //Adding book details to intent
        intent.putExtra("content", post.content);
        intent.putExtra("title",post.title);
        intent.putExtra("author",post.author);

        //Starting another activity to show post details
        startActivity(intent);
    }
}
