package com.example.rolebasedlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Webview extends AppCompatActivity {
    WebView webView;
    String score;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Intent i =getIntent();
        score = i.getStringExtra("score");
//        Toast.makeText(this, "Score" + score, Toast.LENGTH_SHORT).show();
        webView = findViewById(R.id.web);
        webView.loadUrl(score);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }
}