package com.lxkj.sceniccity.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.lxkj.sceniccity.R;
import com.lxkj.sceniccity.app.MyApplication;
import com.lxkj.sceniccity.app.adapter.MyFragmentPagerAdapter;
import com.lxkj.sceniccity.app.ui.camouflage.PointActivity;
import com.lxkj.sceniccity.app.util.SharedPreferencesUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * 引导页
 * Created by Slingge on 2017/5/17 0017.
 */

public class WelComeActivity extends FragmentActivity {
    private boolean flag;

    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Switch();
        init();
    }

    private void init() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        List<Fragment> list = new ArrayList<>();
        WelComeFragment f1 = new WelComeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);
        f1.setArguments(bundle);
        list.add(f1);

        WelComeFragment f2 = new WelComeFragment();
        bundle = new Bundle();
        bundle.putInt("type", 1);
        f2.setArguments(bundle);
        list.add(f2);

        final MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        //拖的时候才进入下一页
                        flag = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        flag = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        /**
                         * 判断是不是最后一页，同时是不是拖的状态
                         */
                        if (viewPager.getCurrentItem() == adapter.getCount() - 1 && !flag) {
                            SharedPreferencesUtil.putSharePre(WelComeActivity.this, "isFirst", true);
                            Intent intent;
                            if (type.equals("0")) {
                                intent = new Intent(WelComeActivity.this, MainActivity.class);
                            } else {
                                intent = new Intent(WelComeActivity.this, PointActivity.class);
                            }
                            startActivity(intent);
                            finish();
                        }
                        flag = true;
                        break;
                }
            }
        });
    }


    //伪装开关
    private void Switch() {
        String json = "{\"cmd\":\"switch\"" + "}";
        OkHttpUtils.post().url(MyApplication.Url).addParams("json", json).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                type = "1";
            }

            @Override
            public void onResponse(String response, int id) {
//                AbLogUtil.e("switch开关。。。。。。。。。。。。", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {//“1”伪装功能，“0”真实功能
                        type = "0";
                    } else {
                        type = "1";
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

}
