package com.lxkj.sceniccity.app.httpUtil;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.lxkj.sceniccity.andbase.util.AbLogUtil;
import com.lxkj.sceniccity.app.MyApplication;
import com.lxkj.sceniccity.app.ui.WebViewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;


/**
 * 获取充值按钮图
 * Created by Slingge on 2017/1/10 0010.
 */

public class getRechargeHttp {
    private String url;

    public void getrecharge(final Context context, final ImageView image) {
        String json = "{\"cmd\":\"getRecharge\"" + "}";

        OkHttpUtils.get().url(MyApplication.Url).addParams("json", json).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                AbLogUtil.e("获取充值图返回//////////", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {
                        ImageLoader.getInstance().displayImage(obj.getString("imageUrl"), image);
                        url = obj.getString("url");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });
    }

}
