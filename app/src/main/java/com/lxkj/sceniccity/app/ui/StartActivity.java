package com.lxkj.sceniccity.app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lxkj.sceniccity.R;
import com.lxkj.sceniccity.andbase.util.AbLogUtil;
import com.lxkj.sceniccity.app.MyApplication;
import com.lxkj.sceniccity.app.ui.newInfo.NewsInfoActivity;
import com.lxkj.sceniccity.app.util.ImageLoaderUtil;
import com.lxkj.sceniccity.app.util.SharedPreferencesUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;


/**
 * Created by Slingge on 2017/1/9 0009.
 */

public class StartActivity extends BaseActivity {

    private Timer timer;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        new ImageLoaderUtil().configImageLoader(this);
        image = (ImageView) findViewById(R.id.image);
        Switch();
    }




    private void toMainActivity() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!SharedPreferencesUtil.getSharePreBoolean(StartActivity.this, "isFirst")) {
                    startActivity(new Intent(StartActivity.this, WelComeActivity.class));
                } else {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                }
                finish();
            }
        };
        timer.schedule(task, 3000);
    }

    //伪装开关
    private void Switch() {
        String json = "{\"cmd\":\"switch\"" + "}";
        OkHttpUtils.post().url(MyApplication.Url).addParams("json", json).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                startActivity(new Intent(StartActivity.this, NewsInfoActivity.class));
            }

            @Override
            public void onResponse(String response, int id) {
                AbLogUtil.e("switch开关。。。。。。。。。。。。", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {//“1”伪装功能，“0”真实功能
                        toMainActivity();
                    } else {
                        startActivity(new Intent(StartActivity.this, NewsInfoActivity.class));
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


}
