package com.lxkj.sunentertainmentcity.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.sunentertainmentcity.andbase.util.AbLogUtil;
import com.lxkj.sunentertainmentcity.andbase.util.AbStrUtil;
import com.lxkj.sunentertainmentcity.app.MyApplication;
import com.lxkj.sunentertainmentcity.R;
import com.lxkj.sunentertainmentcity.app.adapter.GridViewAdapter;
import com.lxkj.sunentertainmentcity.app.dialog.ChipListDlg;
import com.lxkj.sunentertainmentcity.app.dialog.LoginDlg;
import com.lxkj.sunentertainmentcity.app.httpUtil.AnnouncementHttp;
import com.lxkj.sunentertainmentcity.app.httpUtil.CarouselFigureHttp;
import com.lxkj.sunentertainmentcity.app.httpUtil.WebHttp;
import com.lxkj.sunentertainmentcity.app.httpUtil.getGridViewHttp;
import com.lxkj.sunentertainmentcity.app.httpUtil.getRechargeHttp;
import com.lxkj.sunentertainmentcity.app.util.ImageLoaderUtil;
import com.lxkj.sunentertainmentcity.app.util.ToastUtil;
import com.lxkj.sunentertainmentcity.app.view.MyGridView;
import com.lxkj.sunentertainmentcity.app.view.MyListView;
import com.lxkj.sunentertainmentcity.app.view.ScrollText;
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
 * 首页
 * Created by Slingge on 2016/12/30 0030.
 */

public class ShouYeFragment extends BaseFragment implements View.OnClickListener, LoginDlg.loginInterface, ChipListDlg.chipIntegralListener {

    public String userName;
    private LoginDlg dialog;
    private ChipListDlg chipDlg;

    private ScrollText st_notice;
    private RelativeLayout rl_userinfo, rl_user;
    private TextView tv_login;
    private TextView tv_user, tv_integral, tv_ca;//用户名，积分，已签到

    private ImageView image_recharge;//充值图片

    private boolean isSign;//是否签到
    private String continuous;//签到天数

    private MyListView listView;
    private MyGridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shouye, container, false);

        init(view);
        new AnnouncementHttp().getAnnouncement(getActivity(), st_notice);//公告
        new CarouselFigureHttp().getCarouselFigure(getActivity(), ShouYeFragment.this);//轮播图
        new WebHttp().getWeb(getActivity(), listView);//备用网址
        new getGridViewHttp().getJumpUrl(getActivity(), gridView);
        new getRechargeHttp().getrecharge(getActivity(), image_recharge);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!MyApplication.getIntegral().equals("")) {
            tv_integral.setText("积分：" + MyApplication.getIntegral());
        }
    }

    private void init(View view) {
        listView = (MyListView) view.findViewById(R.id.listView);
        listView.setFocusable(false);

        gridView = (MyGridView) view.findViewById(R.id.gridVeiw);
        gridView.setFocusable(false);

        tv_login = (TextView) view.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);
        TextView tv_sign = (TextView) view.findViewById(R.id.tv_sign);
        tv_sign.setOnClickListener(this);
        TextView tv_exchange = (TextView) view.findViewById(R.id.tv_exchange);
        tv_exchange.setOnClickListener(this);
        TextView tv_luck_draw = (TextView) view.findViewById(R.id.tv_luck_draw);
        tv_luck_draw.setOnClickListener(this);

        tv_user = (TextView) view.findViewById(R.id.tv_user);
        tv_integral = (TextView) view.findViewById(R.id.tv_integral);
        tv_ca = (TextView) view.findViewById(R.id.tv_ca);

        dialog = new LoginDlg(getActivity());
        dialog.setOnLoginListener(this);

        chipDlg = new ChipListDlg();
        chipDlg.setChipIntegralListener(this);

        st_notice = (ScrollText) view.findViewById(R.id.st_notice);

        image_recharge = (ImageView) view.findViewById(R.id.image_recharge);
        image_recharge.setOnClickListener(this);

        rl_userinfo = (RelativeLayout) view.findViewById(R.id.rl_userinfo);
        rl_user = (RelativeLayout) view.findViewById(R.id.rl_user);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                dialog.login();
                break;
            case R.id.tv_sign:
                if (isSign) {
                    ToastUtil.showToast(getActivity(), "今天已经签到了");
                } else {
                    signHttp();
                }
                break;
            case R.id.tv_exchange:
                if (MyApplication.getList().size() == 0) {
                    getChip();
                } else {
                    chipDlg.chipDialog(getActivity(), MyApplication.getList());
                }
                break;
            case R.id.tv_luck_draw:
                startActivity(new Intent(getActivity(), LuckPanActivity.class));
                break;
        }
    }


    /**
     * 签到
     */
    private void signHttp() {
        showProgressDialog("");
        String json = "{\"cmd\":\"sign\",\"userNme\":\"" + userName + "\"}";
        OkHttpUtils.post().addParams("json", json).url(MyApplication.Url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissProgressDialog();
            }
            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {
                        tv_ca.setText("已连续签到：" + (Integer.valueOf(continuous) + 1) + "天");
                        tv_integral.setText("积分：" + obj.getString("integral"));
                        AbLogUtil.e("签到返回.........", response);
                        isSign = true;
                    } else {
                        ToastUtil.showToast(getActivity(), obj.getString("resultNote"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissProgressDialog();
            }
        });
    }


    /**
     * 登陆返回结果
     */
    @Override
    public void loginHttp(String content, String Name) {
        AbLogUtil.e("登陆返回..............", content);
        MyApplication.userName = Name;
        userName = Name;
        try {
            JSONObject obj = new JSONObject(content);
            if (obj.getString("result").equals("0")) {
                rl_userinfo.setVisibility(View.VISIBLE);
                rl_user.setVisibility(View.VISIBLE);
                tv_login.setVisibility(View.GONE);
                tv_user.setText("用户名：" + Name);
                tv_integral.setText("积分：" + obj.getString("integral"));
                continuous = obj.getString("continuous");
                tv_ca.setText("已连续签到：" + continuous + "天");
                if (obj.getString("isSign").equals("true")) {
                    isSign = true;
                } else {
                    isSign = false;
                }
            } else {
                ToastUtil.showToast(getActivity(), obj.getString("resultNote"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 兑换筹码返回积分
     */
    @Override
    public void integral(String integral) {
        tv_integral.setText("积分：" + integral);
        MyApplication.integral = integral;
    }

    /**
     * 获取筹码列表
     */
    public void getChip() {
        LoginDlg.showProgress(context, "加载中...");
        String json = "{\"cmd\":\"chipList\"" + "}";
        OkHttpUtils.post().url(MyApplication.Url).addParams("json", json).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(getActivity(), "网路错误，请稍后重试");
                LoginDlg.dissProgress();
            }

            @Override
            public void onResponse(String response, int id) {
                LoginDlg.dissProgress();
                AbLogUtil.e("获取筹码返回......", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {
                        JSONArray array = new JSONArray(obj.getString("chipList"));
                        List<Map<String, String>> list = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            obj = array.getJSONObject(i);
                            Map<String, String> map = new HashMap<>();
                            map.put("id", obj.getString("chipId"));
                            map.put("chip", obj.getString("chipValue"));
                            list.add(map);
                        }
                        MyApplication.list = list;
                        chipDlg.chipDialog(getActivity(), list);
                    } else {
                        ToastUtil.showToast(context, obj.getString("resultNote"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!MyApplication.integral.equals("")) {
            tv_integral.setText(MyApplication.getIntegral());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MyApplication.integral = AbStrUtil.tvTostr(tv_integral);
    }


}
