package com.lxkj.sunentertainmentcity.app.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.lxkj.sunentertainmentcity.andbase.http.AbHttpUtil;
import com.lxkj.sunentertainmentcity.andbase.http.AbRequestParams;
import com.lxkj.sunentertainmentcity.andbase.http.AbStringHttpResponseListener;
import com.lxkj.sunentertainmentcity.andbase.util.AbAppUtil;
import com.lxkj.sunentertainmentcity.andbase.util.AbLogUtil;
import com.lxkj.sunentertainmentcity.app.util.ProgressDialog;
import com.lxkj.sunentertainmentcity.app.util.ToastUtil;


/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class BaseActivity extends Activity {

    protected Context context;
    private Dialog progressDlg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }


    @Override
    public void onDestroy() {
        // 关闭对话框
        dismissProgressDialog();
        super.onDestroy();
    }

    /**
     * 显示进度对话框
     *
     * @param msg 提示文本
     */
    protected void showProgressDialog(String msg) {
        progressDlg = ProgressDialog.createLoadingDialog(context, msg);
        progressDlg.show();
    }

    /**
     * 关闭进度对话框
     */
    protected void dismissProgressDialog() {
        if (progressDlg != null && progressDlg.isShowing()) {
            progressDlg.dismiss();
        }
    }

    /**
     * 关闭该窗口
     *
     * @param clazz
     */
    public void onEventMainThread(Class<?> clazz) {
        if (getClass().getSimpleName().equalsIgnoreCase(clazz.getSimpleName())) {
            finish();
        }
    }

    /**
     * post方式请求(显示加载对话框)
     *
     * @param url    请求地址
     * @param params 参数
     */
    protected void doPost(final String url, AbRequestParams params) {
        this.doPost(url, params, true);
    }

    /**
     * post方式请求
     *
     * @param url                请求地址
     * @param params             请求参数
     * @param showProgressDialog 是否显示进度条对话框
     */
    protected void doPost(final String url, AbRequestParams params,
                          boolean showProgressDialog) {
        this.doPost(url, params, showProgressDialog, null);
    }

    protected void doPost(final String url, AbRequestParams params,
                          String loadingText) {
        this.doPost(url, params, true, loadingText);
    }

    /**
     * post方式请求
     *
     * @param url                请求地址
     * @param params             请求参数
     * @param showProgressDialog 是否显示进度条对话框
     * @param loadingText        对话框显示的内容
     */
    protected void doPost(final String url, AbRequestParams params,
                          boolean showProgressDialog, String loadingText) {
        if (!AbAppUtil.isNetworkAvailable(context)) {
            // 没有网络时提示
            ToastUtil.showToast(context, "没有可用网络");
            return;
        }

        AbHttpUtil.getInstance(context).post(
                url,
                params,
                new MyAbStringHttpResponseListener(url, showProgressDialog,
                        loadingText));
    }

    /**
     * get方式请求
     *
     * @param url 请求地址
     *            是否显示进度条
     */
    protected void doGetWithCache(final String url, boolean cache) {
        AbHttpUtil.getInstance(context).getWithCache(url, null,
                new MyAbStringHttpResponseListener(url, true, ""));
    }

    /**
     * get方式请求
     *
     * @param url                请求地址
     * @param showProgressDialog 是否显示进度条
     */
    protected void doGet(final String url, boolean showProgressDialog) {
        this.doGet(url, null, showProgressDialog);
    }

    /**
     * get方式请求
     *
     * @param url 请求地址
     */
    protected void doGet(String url) {
        this.doGet(url, null, true);
    }

    /**
     * get方式请求
     *
     * @param url                请求地址
     * @param params             参数
     * @param showProgressDialog 是否显示进度条
     */
    protected void doGet(final String url, AbRequestParams params,
                         boolean showProgressDialog) {
        this.doGet(url, params, showProgressDialog, null);
    }

    /**
     * get方式请求
     *
     * @param url
     * @param params
     * @param loadingText
     */
    protected void doGet(final String url, AbRequestParams params,
                         String loadingText) {
        this.doGet(url, params, true, loadingText);
    }

    /**
     * get方式请求
     *
     * @param url                请求地址
     * @param params             参数
     * @param showProgressDialog 是否显示进度条
     * @param loadingText        对话框显示的内容
     */
    protected void doGet(String url, AbRequestParams params,
                         boolean showProgressDialog, String loadingText) {
        if (!AbAppUtil.isNetworkAvailable(context)) {
            // 没有网络时提示
            ToastUtil.showToast(context, "没有可用网络");
            return;
        }

        StringBuilder builder = new StringBuilder(url);
        if (params != null) {
            if (url.indexOf("?") == -1) {
                builder.append("?");
            } else {
                builder.append("&");
            }
            builder.append(params.getParamString());
        }

        AbHttpUtil.getInstance(context).get(
                builder.toString(),
                null,
                new MyAbStringHttpResponseListener(url, showProgressDialog,
                        loadingText));
    }

    /**
     * 对接口返回的数据进行统一格式的解析
     *
     * @param url
     * @param content
     */
    protected void responseDataCallback(String url, String content) {
        AbLogUtil.e(context, content);
    }

    /**
     * 返回的数据没有按照约定的接口格式返回
     *
     * @param content
     */
    protected void callbackErrorJsonFormat(String content) {
        // GlobalApplication.showToast(context,getClass().getSimpleName() +
        // " JSON格式错误："
        // + content);

    }

    /**
     * 请求接口错误
     *
     * @param errNum  自定义的错误码
     * @param url     接口的url
     * @param retData 返回的错误消息提示
     * @param msg
     */
    protected void callbackError(int errNum, String url, String retData,
                                 String msg) {
        // GlobalApplication.showToast(retData);
        // if (2 == errNum) {
        // // 未登录---清楚已有的登录信息，跳转到登录界面
        // User user = new User();
        // GlobalApplication.CURRENT_USER = user;
        // GlobalApplication.spUtil.setUser(user);
        // // openActivity(LoginActivity.class);
        // }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 请求接口成功
     *
     * @param url     请求的地址
     * @param hasNext 总页数
     * @param
     * @param msg
     */
    protected void callbackSuccess(String url, boolean hasNext, String data,
                                   String msg) {

    }


    /**
     * 类说明: 接口通信的回调
     *
     * @author 作者 LUYA, E-mail: 468034043@qq.com
     * @version 创建时间：2015年12月9日 上午10:44:36
     */
    private class MyAbStringHttpResponseListener extends
            AbStringHttpResponseListener {
        private String url;// 接口的url路径
        private boolean showProgressDialog;
        private String loadingText;

        public MyAbStringHttpResponseListener(String url,
                                              boolean showProgressDialog, String loadingText) {
            this.url = url;
            this.showProgressDialog = showProgressDialog;
            this.loadingText = TextUtils.isEmpty(loadingText) ? "加载中..."
                    : loadingText;
        }

        @Override
        public void onStart() {
            if (showProgressDialog) {
                showProgressDialog(loadingText);
            }
        }

        @Override
        public void onFinish() {
            if (showProgressDialog) {
                dismissProgressDialog();
            }
        }

        @Override
        public void onFailure(int statusCode, String content, Throwable error) {
            if (showProgressDialog) {
                dismissProgressDialog();
            }
            callbackFailure(statusCode, content, error);
        }

        @Override
        public void onSuccess(int statusCode, String content) {
            if (showProgressDialog) {
                dismissProgressDialog();
            }
            switch (statusCode) {
                case 200:
                    responseDataCallback(url, content);
                    break;
                case 500:
                    // GlobalApplication.showToast(context,"服务器错误");
                    break;
                default:
                    // GlobalApplication.showToast(context,"其他错误，statusCode=" +
                    // statusCode);
                    break;
            }
        }
    }

    /**
     * 通信失败
     *
     * @param statusCode
     * @param content
     * @param error
     */
    protected void callbackFailure(int statusCode, String content,
                                   Throwable error) {
        ToastUtil.showToast(context, "连接服务器失败");
    }
}
