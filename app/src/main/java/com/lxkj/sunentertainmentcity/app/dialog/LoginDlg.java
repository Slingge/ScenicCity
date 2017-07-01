package com.lxkj.sunentertainmentcity.app.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.sunentertainmentcity.R;
import com.lxkj.sunentertainmentcity.andbase.util.AbStrUtil;
import com.lxkj.sunentertainmentcity.app.MyApplication;
import com.lxkj.sunentertainmentcity.app.util.ProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;


/**
 * 登陆dialog及网络请求
 * Created by Slingge on 2017/1/6 0006.
 */

public class LoginDlg {

    public static Dialog progressDlg;
    private Context context;

    public LoginDlg(Context context) {
        this.context = context;
    }

    private loginInterface pcw;

    public void setOnLoginListener(loginInterface pcw) {
        this.pcw = pcw;
    }
    public interface loginInterface {
        void loginHttp(String content,String Name);
    }

    public void login() {
        final AlertDialog builder = new AlertDialog.Builder(context).create(); // 先得到构造器
        builder.show();
        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.dlg_login, null);
        builder.getWindow().setContentView(view);

        final EditText et_user = (EditText) view.findViewById(R.id.et_user);
        final TextView tv_login = (TextView) view.findViewById(R.id.login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(context,"登陆中...");
                final String userName = AbStrUtil.etTostr(et_user);
                if (AbStrUtil.isEmpty(userName)) {
                    return;
                }
                String json = "{\"cmd\":\"login\",\"userNme\":\"" + userName + "\"}";

                OkHttpUtils.post().url(MyApplication.Url).addParams("json", json).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dissProgress();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        dissProgress();
                        pcw.loginHttp(response,userName);
                    }
                });
                builder.dismiss();
            }
        });

        builder.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        new Timer().schedule(new TimerTask() {
                                 public void run() {
                                     InputMethodManager inputManager =
                                             (InputMethodManager) et_user.getContext()//et_skill，EditText
                                                     .getSystemService(Context.INPUT_METHOD_SERVICE);
                                     inputManager.showSoftInput(et_user, 0);
                                 }
                             },
                100);
    }


    public static void showProgress(Context context,String msg) {
        progressDlg = ProgressDialog.createLoadingDialog(context, msg);
        progressDlg.show();
    }

    public static void dissProgress() {
        if(progressDlg!=null){
            progressDlg.dismiss();
            progressDlg = null;
        }
    }


}
