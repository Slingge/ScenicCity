package com.lxkj.sceniccity.app.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lxkj.sceniccity.R;
import com.lxkj.sceniccity.app.MyApplication;
import com.lxkj.sceniccity.app.dialog.ExtensionChartDlg;
import com.lxkj.sceniccity.app.httpUtil.LuckPanHttp;
import com.lxkj.sceniccity.app.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

import static com.lxkj.sceniccity.R.id.rb1;

/**
 * Created by Slingge on 2016/12/30 0030.
 */

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private RadioGroup radioGroup;

    private ShouYeFragment shouYeFragment;
    private WebViewFragment webViewFragment;
    private NewFragment newFragment;
    private DiscountFragment discountFragment;

    private ImageView image_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getExtensionChart();//推广图
        init();
        new LuckPanHttp(this).getPrize();
        upToken();
    }

    private void init() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        image_back = (ImageView) findViewById(R.id.image_back);

        final RadioButton rb1 = (RadioButton) findViewById(R.id.rb1);
        rb1.setChecked(true);
        rb1.setOnClickListener(this);
        initListener(R.id.rb1);
        RadioButton rb2 = (RadioButton) findViewById(R.id.rb2);
        rb2.setOnClickListener(this);
        RadioButton rb3 = (RadioButton) findViewById(R.id.rb3);
        rb3.setOnClickListener(this);
        RadioButton rb4 = (RadioButton) findViewById(R.id.rb4);
        rb4.setOnClickListener(this);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_back.setVisibility(View.GONE);
                radioGroup.setVisibility(View.VISIBLE);
                initListener(R.id.rb1);
                rb1.setChecked(true);
            }
        });
    }


    /**
     * 监听事件
     */
    private void initListener(int ID) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        goneFragment();
        switch (ID) {
            case rb1:
                image_back.setVisibility(View.GONE);
                radioGroup.setVisibility(View.VISIBLE);
                if (shouYeFragment == null) {
                    shouYeFragment = new ShouYeFragment();
                    ft.add(R.id.FrameAct_FragmentGroup, shouYeFragment);
                } else {
                    ft.show(shouYeFragment);
                }
                break;
            case R.id.rb2:
                if (discountFragment == null) {
                    discountFragment = new DiscountFragment();
                    ft.add(R.id.FrameAct_FragmentGroup, discountFragment);
                } else {
                    ft.show(discountFragment);
                }
                image_back.setVisibility(View.GONE);
                radioGroup.setVisibility(View.VISIBLE);
                break;
            case R.id.rb3:
                if (newFragment == null) {
                    newFragment = new NewFragment();
                    ft.add(R.id.FrameAct_FragmentGroup, newFragment);
                } else {
                    ft.show(newFragment);
                }
                image_back.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.GONE);
                break;
            case R.id.rb4:
                if (webViewFragment == null) {
                    webViewFragment = new WebViewFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("flag", 2);
                    webViewFragment.setArguments(bundle);
                    ft.add(R.id.FrameAct_FragmentGroup, webViewFragment);
                } else {
                    ft.show(webViewFragment);
                }

                image_back.setVisibility(View.GONE);
                radioGroup.setVisibility(View.VISIBLE);
                break;
        }
        ft.commit();
    }


    //隐藏所有fragment方法
    private void goneFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (shouYeFragment != null) {
            ft.hide(shouYeFragment);
        }
        if (discountFragment != null) {
            ft.hide(discountFragment);
        }
        if (webViewFragment != null) {
            ft.hide(webViewFragment);
        }
        if (newFragment != null) {
            ft.hide(newFragment);
        }
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        initListener(v.getId());
    }


    // 确认退出
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("退出确认");
            dialog.setMessage("确认要退出应用么？");
            dialog.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                            // TODO Auto-generated method stub
                            finish();
                            System.exit(0);
                        }
                    });
            dialog.setNeutralButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                            // TODO Auto-generated method stub
                        }
                    });
            dialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 获取推广图
     */
    private void getExtensionChart() {
        String json = "{\"cmd\":\"getExtensionChart\"" + "}";
        OkHttpUtils.post().addParams("json", json).url(MyApplication.Url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(MainActivity.this, "网络错误，请稍后重试");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {
                        new ExtensionChartDlg().showDialog(MainActivity.this, obj.getString("url"));
                    } else {
                        ToastUtil.showToast(MainActivity.this, obj.getString("resultNote"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void upToken() {
        String token = JPushInterface.getRegistrationID(this);
        String json = "{\"cmd\":\"token\",\"token\":\"" + token + "\"}";
        OkHttpUtils.post().url(MyApplication.Url).addParams("json", json).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }
            @Override
            public void onResponse(String response, int id) {
            }
        });
    }

}
