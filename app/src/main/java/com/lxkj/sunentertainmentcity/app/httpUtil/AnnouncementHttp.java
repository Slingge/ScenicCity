package com.lxkj.sunentertainmentcity.app.httpUtil;

import android.content.Context;

import com.lxkj.sunentertainmentcity.app.MyApplication;
import com.lxkj.sunentertainmentcity.app.util.ToastUtil;
import com.lxkj.sunentertainmentcity.app.view.ScrollText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;


/**
 * 获取公告网络请求
 * Created by Slingge on 2017/1/6 0006.
 */

public class AnnouncementHttp {

    public void getAnnouncement(final Context context, final ScrollText st_notice) {
        String json = "{\"cmd\":\"getAnnouncement\"" + "}";
        OkHttpUtils.post().addParams("json", json).url(MyApplication.Url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {
                        st_notice.setText(obj.getString("data"));
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
