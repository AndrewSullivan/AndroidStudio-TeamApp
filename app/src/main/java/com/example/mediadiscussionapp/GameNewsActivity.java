package com.example.mediadiscussionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GameNewsActivity extends AppCompatActivity {

    WebView wv_game_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_news);

        wv_game_news = findViewById(R.id.wv_game_news);

        wv_game_news.setWebViewClient(new WebViewClient());
        wv_game_news.loadUrl("https://www.ign.com/uk");
    }
}