package com.lxkj.sunentertainmentcity.app.httpUtil;

import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import com.lxkj.sunentertainmentcity.andbase.util.AbLogUtil;
import com.lxkj.sunentertainmentcity.app.MyApplication;
import com.lxkj.sunentertainmentcity.app.adapter.NewListAdapter;
import com.lxkj.sunentertainmentcity.app.dialog.LoginDlg;
import com.lxkj.sunentertainmentcity.app.ui.NewActivity;
import com.lxkj.sunentertainmentcity.app.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * 获取消息网络请求
 * Created by Slingge on 2017/1/6 0006.
 */

public class NewHttp {

    private Context context;
    private ListView listView;

    private List<Map<String, String>> list;

    public NewHttp(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    public void getNew() {
        LoginDlg.showProgress(context, "加载中...");
        String json = "{\"cmd\":\"getMsg\"" + "}";
        OkHttpUtils.get().addParams("json", json).url(MyApplication.Url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(context, "网络错误");
                LoginDlg.dissProgress();
            }

            @Override
            public void onResponse(String response, int id) {
                LoginDlg.dissProgress();
                AbLogUtil.e("获取消息返回", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {
                        JSONArray array = new JSONArray(obj.getString("data"));
                        list = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            obj = array.getJSONObject(i);
                            Map<String, String> map = new HashMap<>();
                            map.put("msg", obj.getString("msg"));
                            map.put("url", obj.getString("url"));
                            map.put("time", obj.getString("time"));
                            list.add(map);
                        }
                        NewListAdapter adapter = new NewListAdapter(context, list);
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

    public void onItem(int i) {
        Intent intent = new Intent(context, NewActivity.class);
        intent.putExtra("new", list.get(i).get("url"));
        context.startActivity(intent);
    }

}
