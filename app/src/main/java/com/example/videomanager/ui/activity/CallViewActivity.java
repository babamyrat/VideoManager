package com.example.videomanager.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.videomanager.R;

public class CallViewActivity extends AppCompatActivity {
private WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_view);
        myWebView = findViewById(R.id.webView);
        myWebView.loadUrl("https://videomanager-test.garpix.com/");
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}