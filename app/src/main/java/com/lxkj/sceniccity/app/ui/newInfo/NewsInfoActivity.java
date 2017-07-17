package com.lxkj.sceniccity.app.ui.newInfo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

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

public class NewsInfoActivity extends Activity implements View.OnClickListener {

    private NewInfoAdapter adapter;
    private MyListView recyclerView;

    private NewInfoBean bean;

    private final String Url = "http://op.juhe.cn/onebox/basketball/nba?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        init();
        getNew();
    }


    private void init() {
        ImageView image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setVisibility(View.GONE);
        ImageView tv_title = (ImageView) findViewById(R.id.tv_title);
        tv_title.setVisibility(View.GONE);
        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);

        TextView tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setOnClickListener(this);

        recyclerView = (MyListView) findViewById(R.id.recyclerView);
    }


    private void getNew() {
        ProgressDialog.createLoadingDialog(this, "加载中...");
        OkHttpUtils.post().url(Url).addParams("key", "fbfb827480f6333ff2afbf2ea0d0941c").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(NewsInfoActivity.this, "网络错误");
            }

            @Override
            public void onResponse(String response, int id) {
                bean = new Gson().fromJson(response, NewInfoBean.class);
                if (bean.reason.equals("查询成功")) {
                    adapter = new NewInfoAdapter(NewsInfoActivity.this, bean.result.list.get(0).tr);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:

                break;
        }
    }

}
