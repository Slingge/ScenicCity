package com.lxkj.sunentertainmentcity.app.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxkj.sunentertainmentcity.R;
import com.lxkj.sunentertainmentcity.app.MyApplication;
import com.lxkj.sunentertainmentcity.app.httpUtil.UpPrizeHttp;
import com.lxkj.sunentertainmentcity.app.util.ToastUtil;
import com.lxkj.sunentertainmentcity.luckpan.LuckPanLayout;
import com.lxkj.sunentertainmentcity.luckpan.RotatePan;
import com.lxkj.sunentertainmentcity.luckpan.Util;

import java.util.List;
import java.util.Map;


/**
 * 抽奖转盘
 * Created by Slinggeon 2017/1/3 0003.
 */

public class LuckPanActivity extends BaseActivity implements RotatePan.AnimationEndListener, UpPrizeHttp.upPrizeListener {

    private RotatePan rotatePan;
    private LuckPanLayout luckPanLayout;
    private ImageView goBtn;

    private TextView tv_integral;
    private int integral = 0;//积分
    private int count;

    private List<Map<String, String>> mapList;

    private UpPrizeHttp upPrizeHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_lottery_wheel);
        initView();
        if (MyApplication.mapList == null) {
            ToastUtil.showToast(this, "获取奖品失败，请稍后重试");
            return;
        }
        mapList = MyApplication.getMapListp();
        init();
    }


    @Override
    protected void onStart() {
        super.onStart();
        integral = Integer.valueOf(MyApplication.getIntegral().substring(3, MyApplication.getIntegral().length()));
        tv_integral.setText("剩余积分：" + integral + "");
    }


    public Context getContext() {
        return this;
    }

    @Override
    public void endAnimation(int position) {

        luckPanLayout.setDelayTime(500);
        String prizeId = "";
        count=position;
        if (position == 7) {
            prizeId = mapList.get(0).get("prizeId").toString();
        } else if (position == 6) {
            prizeId = mapList.get(0).get("prizeId").toString();
        } else {
            prizeId = mapList.get(position + 1).get("prizeId").toString();
        }
        upPrizeHttp.upPrizeHttp(prizeId);
        goBtn.setEnabled(true);
    }

    //返回上传后的剩余积分
    @Override
    public void ResidualIntegral(String strIntegral) {
        integral = Integer.valueOf(strIntegral);
        tv_integral.setText("剩余积分：" + strIntegral);
        if (count == 7) {
            Toast.makeText(this, "恭喜抽中了" + mapList.get(0).get("prize").toString(), Toast.LENGTH_SHORT).show();
        } else if (count == 6) {
            Toast.makeText(this, "恭喜抽中了" + mapList.get(0).get("prize").toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "恭喜抽中了" + mapList.get(count + 1).get("prize").toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void initView() {
        tv_integral = (TextView) findViewById(R.id.tv_integral);

        upPrizeHttp = new UpPrizeHttp(this);
        upPrizeHttp.setupPrizeListener(this);

        ImageView image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.integral = "积分：" + integral;
    }

    private void init() {
        luckPanLayout = (LuckPanLayout) findViewById(R.id.luckpan_layout);
        luckPanLayout.startLuckLight();
        rotatePan = (RotatePan) findViewById(R.id.rotatePan);
        rotatePan.setAnimationEndListener(this);
        goBtn = (ImageView) findViewById(R.id.go);

        luckPanLayout.post(new Runnable() {
            @Override
            public void run() {
                int height = getWindow().getDecorView().getHeight();
                int width = getWindow().getDecorView().getWidth();

                int backHeight = 0;

                int MinValue = Math.min(width, height);
                MinValue -= Util.dip2px(LuckPanActivity.this, 10) * 2;
                backHeight = MinValue / 2;

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) luckPanLayout.getLayoutParams();
                lp.width = MinValue;
                lp.height = MinValue;

                luckPanLayout.setLayoutParams(lp);

                MinValue -= Util.dip2px(LuckPanActivity.this, 28) * 2;
                lp = (RelativeLayout.LayoutParams) rotatePan.getLayoutParams();
                lp.height = MinValue;
                lp.width = MinValue;

                rotatePan.setLayoutParams(lp);


                lp = (RelativeLayout.LayoutParams) goBtn.getLayoutParams();
                lp.topMargin += backHeight;
                lp.topMargin -= (goBtn.getHeight() / 2);
                goBtn.setLayoutParams(lp);

                getWindow().getDecorView().requestLayout();
            }
        });
    }


    public void rotation(View view) {
        if (integral < 20) {
            ToastUtil.showToast(LuckPanActivity.this, "您的积分不足");
            return;
        }
        rotatePan.startRotate(-1);
        luckPanLayout.setDelayTime(100);
        goBtn.setEnabled(false);
    }

}
