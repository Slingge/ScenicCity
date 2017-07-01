package com.lxkj.sunentertainmentcity.app.ui;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.lxkj.sunentertainmentcity.R;
import com.lxkj.sunentertainmentcity.app.view.MyWebView;


/**
 * Created by Slingge on 2017/1/6 0006.
 */

public class WebViewActivity extends BaseActivity {

    WebView webview;
    private String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_webview);

        Url = getIntent().getStringExtra("url");

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        webview.loadUrl(Url);
    }

    private void init() {
        MyWebView myWebView = (MyWebView) findViewById(R.id.webview);
        webview = myWebView.getWebView();
        WebSettings settings = webview.getSettings();
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置支持js
        settings.setJavaScriptEnabled(true);
        // 关闭缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 支持自动加载图片
        settings.setLoadsImagesAutomatically(true);
        // 设置出现缩放工具
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        // 扩大比例的缩放
        settings.setUseWideViewPort(true);
        // 自适应屏幕
        settings.setLoadWithOverviewMode(true);
    }

}
