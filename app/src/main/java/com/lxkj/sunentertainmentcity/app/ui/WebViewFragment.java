package com.lxkj.sunentertainmentcity.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.lxkj.sunentertainmentcity.R;
import com.lxkj.sunentertainmentcity.app.MyApplication;
import com.lxkj.sunentertainmentcity.app.dialog.LoginDlg;
import com.lxkj.sunentertainmentcity.app.util.ToastUtil;
import com.lxkj.sunentertainmentcity.app.view.MyWebView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

import static com.lxkj.sunentertainmentcity.R.drawable.e;

/**
 * Created by Slingge on 2016/12/30 0030.
 */

public class WebViewFragment extends BaseFragment {

    private WebView webview;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        init(view);
        getUrl();
        return view;
    }

    private void init(View view) {
        MyWebView myWebView = (MyWebView) view.findViewById(R.id.webview);
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

    private void getUrl() {
        LoginDlg.showProgress(getActivity(), "加载中...");
        String json = "{\"cmd\":\"registerUrl\"" + "}";
        OkHttpUtils.get().url(MyApplication.Url).addParams("json", json).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(getActivity(), "网络错误");
                LoginDlg.dissProgress();
            }

            @Override
            public void onResponse(String response, int id) {
                LoginDlg.dissProgress();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {
                        webview.loadUrl(obj.getString("url"));
                    } else {
                        ToastUtil.showToast(getActivity(), obj.getString("resultNote"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
