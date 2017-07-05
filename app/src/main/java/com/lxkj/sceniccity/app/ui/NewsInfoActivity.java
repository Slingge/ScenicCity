package com.lxkj.sceniccity.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.TabLayout;
import android.support.v4.text.TextUtilsCompat;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TableLayout;
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

import org.w3c.dom.Text;

import okhttp3.Call;


/**
 * Created by Slingge on 2017/7/4 0004.
 */

public class NewsInfoActivity extends Activity implements View.OnClickListener {

    private NewInfoAdapter adapter;
    private MyListView recyclerView;

    private NewInfoBean.resultBean resultBean;

    private String Url = "http://v.juhe.cn/toutiao/index";

    TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        init();
        getNew("top");
        setColor(tv_1);
    }

    private void setColor(TextView tv) {
        tv_1.setTextColor(getResources().getColor(R.color.black));
        tv_2.setTextColor(getResources().getColor(R.color.black));
        tv_3.setTextColor(getResources().getColor(R.color.black));
        tv_4.setTextColor(getResources().getColor(R.color.black));
        tv_5.setTextColor(getResources().getColor(R.color.black));
        tv_6.setTextColor(getResources().getColor(R.color.black));
        tv_7.setTextColor(getResources().getColor(R.color.black));
        tv_8.setTextColor(getResources().getColor(R.color.black));
        tv_9.setTextColor(getResources().getColor(R.color.black));

        tv.setTextColor(getResources().getColor(R.color.AsukaColor));
    }

    private void init() {
        ImageView image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setVisibility(View.GONE);

        recyclerView = (MyListView) findViewById(R.id.recyclerView);
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(NewsInfoActivity.this, WebViewActivity.class);
                intent.putExtra("url", resultBean.data.get(position).url);
                intent.putExtra("flag", 0);
                startActivity(intent);
            }
        });

        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_1.setOnClickListener(this);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_2.setOnClickListener(this);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_3.setOnClickListener(this);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_4.setOnClickListener(this);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_5.setOnClickListener(this);
        tv_6 = (TextView) findViewById(R.id.tv_6);
        tv_6.setOnClickListener(this);
        tv_7 = (TextView) findViewById(R.id.tv_7);
        tv_7.setOnClickListener(this);
        tv_8 = (TextView) findViewById(R.id.tv_8);
        tv_8.setOnClickListener(this);
        tv_9 = (TextView) findViewById(R.id.tv_9);
        tv_9.setOnClickListener(this);
    }


    private void getNew(String str) {
        ProgressDialog.createLoadingDialog(this, "加载中...");
        OkHttpUtils.post().url(Url).addParams("type", str).addParams("key", "8acf2782168a52c3d719f96737958fdb").build().execute(new StringCallback() {
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                getNew("top");
                setColor(tv_1);
                break;
            case R.id.tv_2:
                getNew("shehui");
                setColor(tv_2);
                break;
            case R.id.tv_3:
                getNew("guonei");
                setColor(tv_3);
                break;
            case R.id.tv_4:
                getNew("tiyu");
                setColor(tv_4);
                break;
            case R.id.tv_5:
                getNew("junshi");
                setColor(tv_5);
                break;
            case R.id.tv_6:
                getNew("keji");
                setColor(tv_6);
                break;
            case R.id.tv_7:
                getNew("caijing");
                setColor(tv_7);
                break;
            case R.id.tv_8:
                getNew("shishang");
                setColor(tv_8);
                break;
            case R.id.tv_9:
                getNew("guoji");
                setColor(tv_9);
                break;
        }
    }

}
