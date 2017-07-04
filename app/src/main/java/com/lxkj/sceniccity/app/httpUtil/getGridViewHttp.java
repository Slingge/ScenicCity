package com.lxkj.sceniccity.app.httpUtil;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lxkj.sceniccity.andbase.util.AbLogUtil;
import com.lxkj.sceniccity.app.MyApplication;
import com.lxkj.sceniccity.app.adapter.GridViewAdapter;
import com.lxkj.sceniccity.app.ui.WebViewActivity;
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
 * Created by Slingge on 2017/1/10 0010.
 */

public class getGridViewHttp {

    public void getJumpUrl(final Context context, final GridView gridView) {
        final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String json = "{\"cmd\":\"getJumpUrl\"" + "}";
        OkHttpUtils.get().url(MyApplication.Url).addParams("json", json).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                AbLogUtil.e(" 获取GRidView失败,", e.toString());
                ToastUtil.showToast(context, "获取GRidView失败," + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {
                        JSONArray array = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < array.length(); i++) {
                            obj = array.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("id", obj.getString("id"));
                            map.put("url", obj.getString("url"));
                            list.add(map);
                        }
                        GridViewAdapter adapter1 = new GridViewAdapter(context, list);
                        gridView.setAdapter(adapter1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AbLogUtil.e("获取GridView..........", response);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", list.get(position).get("url"));
                context.startActivity(intent);
            }
        });
    }

}
