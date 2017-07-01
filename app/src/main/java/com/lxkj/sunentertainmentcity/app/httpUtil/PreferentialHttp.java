package com.lxkj.sunentertainmentcity.app.httpUtil;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lxkj.sunentertainmentcity.andbase.util.AbLogUtil;
import com.lxkj.sunentertainmentcity.app.MyApplication;
import com.lxkj.sunentertainmentcity.app.adapter.DiscountListAdapter;
import com.lxkj.sunentertainmentcity.app.dialog.LoginDlg;
import com.lxkj.sunentertainmentcity.app.ui.WebViewActivity;
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

import static com.lxkj.sunentertainmentcity.R.drawable.e;

/**
 * 获取优惠
 * Created by Slingge on 2017/1/10 0010.
 */

public class PreferentialHttp {

    public void getPreferential(final Context context, final ListView listView) {
        LoginDlg.showProgress(context, "加载中...");
        final List<Map<String, String>> list = new ArrayList<>();
        String json = "{\"cmd\":\"getPreferential\"" + "}";
        OkHttpUtils.get().url(MyApplication.Url).addParams("json", json).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(context, "网络错误");
                LoginDlg.dissProgress();
            }

            @Override
            public void onResponse(String response, int id) {
                AbLogUtil.e("获取优惠活动放回//////////", response);
                LoginDlg.dissProgress();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {
                        JSONArray array = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < array.length(); i++) {
                            obj = array.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("imageUrl", obj.getString("imageUrl"));
                            map.put("url", obj.getString("url"));
                            list.add(map);
                        }
                        DiscountListAdapter adapter = new DiscountListAdapter(context, list);
                        listView.setAdapter(adapter);
                    } else {
                        ToastUtil.showToast(context, obj.getString("resultNote"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent inten = new Intent(context, WebViewActivity.class);
                inten.putExtra("url", list.get(position).get("url"));
                context.startActivity(inten);
            }
        });
    }

}
