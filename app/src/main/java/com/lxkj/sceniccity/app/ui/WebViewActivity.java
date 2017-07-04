package com.lxkj.sceniccity.app.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lxkj.sceniccity.R;
import com.lxkj.sceniccity.app.view.MyWebView;


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
        ImageView image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        if (getIntent().getIntExtra("flag", -1) != 0) {
            rl.setVisibility(View.GONE);
        }


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
