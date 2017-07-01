package com.lxkj.sunentertainmentcity.app;

import android.app.Application;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.lxkj.sunentertainmentcity.andbase.util.AbLogUtil;
import com.lxkj.sunentertainmentcity.app.util.ImageLoaderUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.MemoryCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * Created by Slingge on 17/12/30 0030.
 */
public class MyApplication extends Application {

    public static String Url = "http://122.114.53.130:8070/suncityService/service.action";

    public static List<Map<String, String>> list = new ArrayList<>();
    public static String userName = "";
    public static String integral = "";
    public static List<Map<String, String>> mapList;

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderUtil.configImageLoad(getApplicationContext());
        AbLogUtil.E = false;

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        JPushInterface.getRegistrationID(this);

        ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

        CookieJarImpl cookieJar = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .cookieJar(cookieJar)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


    /**
     * 筹码列表
     */
    public static List<Map<String, String>> getList() {
        return list;
    }

    /**
     * 用户名
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * 积分
     */
    public static String getIntegral() {
        return integral;
    }

    /**
     * 抽将转盘奖项
     */
    public static List<Map<String, String>> getMapListp() {
        return mapList;
    }

}
