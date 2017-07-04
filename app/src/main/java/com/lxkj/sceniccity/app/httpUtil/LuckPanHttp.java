package com.lxkj.sceniccity.app.httpUtil;

import android.content.Context;

import com.lxkj.sceniccity.andbase.util.AbLogUtil;
import com.lxkj.sceniccity.app.MyApplication;
import com.lxkj.sceniccity.app.dialog.LoginDlg;
import com.lxkj.sceniccity.app.util.ToastUtil;
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
 * 获取抽奖转盘奖品
 * Created by Slingge on 2017/1/7 0007.
 */

public class LuckPanHttp {

    private Context context;
    public static List<Map<String, String>> mapList;

    public LuckPanHttp(Context context) {
        this.context = context;
    }

    public void getPrize() {
        String json = "{\"cmd\":\"getPrize\"" + "}";
        OkHttpUtils.get().addParams("json", json).url(MyApplication.Url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LoginDlg.dissProgress();
            }

            @Override
            public void onResponse(String response, int id) {
                LoginDlg.dissProgress();
                AbLogUtil.e("获取抽奖返回、、、、、、、、", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {
                        JSONArray array = new JSONArray(obj.getString("data"));
                        mapList=new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            obj = array.getJSONObject(i);
                            Map<String, String> map = new HashMap<>();
                            map.put("prize",obj.getString("prize"));
                            map.put("prizeId",obj.getString("prizeId"));
                            mapList.add(map);
                        }
                        MyApplication.mapList=mapList;
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
