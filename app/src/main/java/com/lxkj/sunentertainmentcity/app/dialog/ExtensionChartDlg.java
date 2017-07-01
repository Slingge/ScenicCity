package com.lxkj.sunentertainmentcity.app.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lxkj.sunentertainmentcity.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 推广图dialog
 * Created by Slingge on 2017/1/6 0006.
 */

public class ExtensionChartDlg {

    public void showDialog(Activity context, String url) {
        final AlertDialog dialog = new AlertDialog.Builder(context, R.style.Dialog).create(); // 先得到构造器
        dialog.show();
        dialog.setCancelable(false);//点击空白不消失
        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.dlg_extension_photo, null);
        dialog.getWindow().setContentView(view);

        ImageView image = (ImageView) view.findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(url, image);
        ImageView image_close = (ImageView) view.findViewById(R.id.image_close);
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);//显示在底部
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = d.getHeight(); // 高度设置为屏幕的0.5
        p.width = d.getWidth(); // 宽度设置为屏幕宽
        dialogWindow.setAttributes(p);
    }


}
