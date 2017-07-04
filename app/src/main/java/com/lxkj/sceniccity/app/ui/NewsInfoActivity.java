package com.lxkj.sceniccity.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.lxkj.sceniccity.R;
import com.lxkj.sceniccity.app.adapter.NewInfoAdapter;
import com.lxkj.sceniccity.app.bean.NewInfoBean;
import com.lxkj.sceniccity.app.util.ProgressDialog;
import com.lxkj.sceniccity.app.util.ToastUtil;
import com.lxkj.sceniccity.app.view.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


/**
 * Created by Slingge on 2017/7/4 0004.
 */

public class NewsInfoActivity extends Activity {

    private NewInfoAdapter adapter;
    private MyListView recyclerView;

    private NewInfoBean.resultBean resultBean;

    private String Url = "http://v.juhe.cn/toutiao/index";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        init();
        getNew();
    }


    private void init() {
        recyclerView = (MyListView) findViewById(R.id.recyclerView);
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showToast(NewsInfoActivity.this, position + "");
                Intent intent = new Intent(NewsInfoActivity.this, WebViewActivity.class);
                intent.putExtra("url", resultBean.data.get(position).url);
                intent.putExtra("flag", 0);
                startActivity(intent);
            }
        });
    }


    private void getNew() {
        ProgressDialog.createLoadingDialog(this, "加载中...");
        OkHttpUtils.post().url(Url).addParams("type", "tiyu").addParams("key", "8acf2782168a52c3d719f96737958fdb").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(NewsInfoActivity.this, "网络错误");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("获取的咨询...........", response);
                NewInfoBean bean = new Gson().fromJson(response, NewInfoBean.class);
                if (bean.reason.equals("成功的返回")) {
                    resultBean = bean.result;
                    adapter = new NewInfoAdapter(NewsInfoActivity.this, resultBean);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }


}
