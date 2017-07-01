package com.lxkj.sunentertainmentcity.app.httpUtil;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.widget.ListView;

import com.lxkj.sunentertainmentcity.app.MyApplication;
import com.lxkj.sunentertainmentcity.app.adapter.UrlListAdapter;
import com.lxkj.sunentertainmentcity.app.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * 获取备用网址网络请求
 * Created by Slingge on 2017/1/6 0006.
 */

public class WebHttp {

    public void getWeb(final Activity context, final ListView listView) {
        String json = "{\"cmd\":\"getWeb\"" + "}";
        OkHttpUtils.post().addParams("json", json).url(MyApplication.Url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {
                        JSONArray array = new JSONArray(obj.getString("data"));
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            obj = array.getJSONObject(i);
                            list.add(obj.getString("url"));
                        }
                        WindowManager wm = context.getWindowManager();
                        int width = wm.getDefaultDisplay().getWidth();
                        UrlListAdapter adapter = new UrlListAdapter(context, list, width);
                        listView.setAdapter(adapter);
                    } else {
                        ToastUtil.showToast(context, obj.getString("resultNote"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
