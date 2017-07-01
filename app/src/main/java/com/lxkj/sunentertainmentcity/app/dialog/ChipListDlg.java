package com.lxkj.sunentertainmentcity.app.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lxkj.sunentertainmentcity.R;
import com.lxkj.sunentertainmentcity.andbase.util.AbLogUtil;
import com.lxkj.sunentertainmentcity.app.MyApplication;
import com.lxkj.sunentertainmentcity.app.adapter.ChipListAdapter;
import com.lxkj.sunentertainmentcity.app.util.ToastUtil;
import com.lxkj.sunentertainmentcity.app.view.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by Slingge on 2017/1/6 0006.
 */

public class ChipListDlg {

    private String chipId = "";


    public interface chipIntegralListener {
        void integral(String integral);
    }

    public chipIntegralListener listener;

    public void setChipIntegralListener(chipIntegralListener listener) {
        this.listener = listener;
    }


    public void chipDialog(final Activity context, final List<Map<String, String>> list) {
        final AlertDialog builder = new AlertDialog.Builder(context, R.style.Dialog).create(); // 先得到构造器
        builder.show();
        LayoutInflater factory = LayoutInflater.from(context);
        final View view = factory.inflate(R.layout.dlg_chiplist, null);
        builder.getWindow().setContentView(view);

        final ListView chipList = (MyListView) view.findViewById(R.id.chipList);
        final ChipListAdapter adapter = new ChipListAdapter(context, list);
        chipList.setAdapter(adapter);
        chipList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedItem(position);
                adapter.notifyDataSetChanged();
                chipId = list.get(position).get("id");
            }
        });

        TextView tv_change = (TextView) view.findViewById(R.id.tv_change);
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chipId.equals("")) {
                    ToastUtil.showToast(context, "请选择要兑换的筹码");
                    return;
                }
                LoginDlg.showProgress(context, "兑换处理中...");
                String json = "{\"cmd\":\"chargeChip\",\"chipId\":\"" + chipId
                        + "\",\"userNme\":\"" + MyApplication.getUserName() + "\"}";
                OkHttpUtils.post().url(MyApplication.Url).addParams("json", json).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, "网络错误");
                        LoginDlg.dissProgress();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LoginDlg.dissProgress();
                        AbLogUtil.e("兑换返回。。。。。。", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getString("result").equals("0")) {
                                ToastUtil.showToast(context, "兑换完成");
                                listener.integral(obj.getString("integral"));
                            } else {
                                ToastUtil.showToast(context, obj.getString("resultNote"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.dismiss();
            }
        });
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        Window dialogWindow = builder.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);//显示在底部
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = d.getWidth(); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
    }

}
