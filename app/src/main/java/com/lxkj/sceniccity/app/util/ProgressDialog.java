package com.lxkj.sceniccity.app.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.sceniccity.R;
import com.lxkj.sceniccity.andbase.util.AbStrUtil;


/**
 * 类说明: 自定义ProgressDialog
 */
public class ProgressDialog {

    public static void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dlg_progress_bar, null);// 得到加载view
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_view);// 加载布局

        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        if (AbStrUtil.isEmpty(msg)) {
            tipTextView.setVisibility(View.GONE);
        }else{
            tipTextView.setText(msg);// 设置加载信息
        }
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
        loadingDialog.setCancelable(true);// 可以用“返回键”

        return loadingDialog;
    }


}
