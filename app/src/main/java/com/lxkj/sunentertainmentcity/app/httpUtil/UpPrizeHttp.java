package com.lxkj.sunentertainmentcity.app.httpUtil;

import android.content.Context;

import com.lxkj.sunentertainmentcity.andbase.util.AbLogUtil;
import com.lxkj.sunentertainmentcity.app.MyApplication;
import com.lxkj.sunentertainmentcity.app.dialog.LoginDlg;
import com.lxkj.sunentertainmentcity.app.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

import static com.lxkj.sunentertainmentcity.app.MyApplication.integral;


/**
 * 上传抽中奖品
 * Created by Slingge on 2017/1/7 0007.
 */

public class UpPrizeHttp {


    private Context context;

    public UpPrizeHttp(Context context) {
        this.context = context;
    }

    public interface upPrizeListener {
        void ResidualIntegral(String strIntegral);
    }

    public upPrizeListener prizeListener;

    public void setupPrizeListener(upPrizeListener prizeListener) {
        this.prizeListener = prizeListener;
    }

    public void upPrizeHttp(String prizeId) {
        LoginDlg.showProgress(context, "上传奖品信息...");
        String json = "{\"cmd\":\"upPrize\",\"prizeId\":\"" + prizeId
                + "\",\"userNme\":\"" + MyApplication.getUserName() + "\"}";
        OkHttpUtils.post().url(MyApplication.Url).addParams("json", json).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(context, "网络错误");
                LoginDlg.dissProgress();
            }

            @Override
            public void onResponse(String response, int id) {
                AbLogUtil.e("上传奖品返回..............", response);
                LoginDlg.dissProgress();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("0")) {
                        integral = obj.getString("integral");
                        prizeListener.ResidualIntegral(obj.getString("integral"));
                    } else {
                        ToastUtil.showToast(context, obj.getString("resultNote"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
