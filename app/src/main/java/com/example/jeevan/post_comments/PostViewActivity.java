package com.example.jeevan.post_comments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PostViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        TextView textViewcontent = (TextView)findViewById(R.id.content);
        textViewcontent.setTextSize(40);
        textViewcontent.setText(content);
        TextView textViewtitle = (TextView)findViewById(R.id.title);
        textViewtitle.setTextSize(40);
        textViewtitle.setText(title);
        TextView textViewauthor = (TextView)findViewById(R.id.author);
        textViewauthor.setTextSize(40);
        textViewauthor.setText(author);
    }
}
