package com.example.mangxahoigump.Controller;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mangxahoigump.R;

public class TinTuc extends AppCompatActivity {
    WebView wv;
    String url = "https://vnexpress.net/";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tin_tuc);
        init();
        wv.loadUrl(url);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
    }
    private void init(){
        wv = findViewById(R.id.wv_tin_tuc);
    }
}
