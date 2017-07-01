package com.lxkj.sunentertainmentcity.app.httpUtil;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.lxkj.sunentertainmentcity.PictureCarousel.CycleViewPagerUtil;
import com.lxkj.sunentertainmentcity.andbase.util.AbLogUtil;
import com.lxkj.sunentertainmentcity.app.MyApplication;
import com.lxkj.sunentertainmentcity.app.dialog.LoginDlg;
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
 * 获取轮播图网络请求
 * Created by Slingge on 2017/1/6 0006.
 */

public class CarouselFigureHttp {

    public void getCarouselFigure(final Context context, final Fragment fragment) {
        LoginDlg.showProgress(context, "加载中...");
        String json = "{\"cmd\":\"CarouselFigure\"" + "}";
        OkHttpUtils.post().addParams("json", json).url(MyApplication.Url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(context, "网络错误，请稍后重试");
                LoginDlg.dissProgress();
            }

            @Override
            public void onResponse(String response, int id) {
                AbLogUtil.e("轮播图返回.....", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {
                        JSONArray array = new JSONArray(obj.getString("data"));
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            obj = array.getJSONObject(i);
                            list.add(obj.getString("url"));
                        }
                        if (list.size() == 0) {
                            list.add("...");
                        }
                        CycleViewPagerUtil.initialize(list, fragment);//轮播图
                    } else {
                        ToastUtil.showToast(context, obj.getString("resultNote"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LoginDlg.dissProgress();
            }
        });
    }

}
