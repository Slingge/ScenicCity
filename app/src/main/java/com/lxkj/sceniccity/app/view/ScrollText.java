package com.lxkj.sceniccity.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 跑马灯TextView
 * 不能控制速度
 * Created by Administrator on 2016/9/28 0028.
 */
public class ScrollText extends TextView {
    public ScrollText(Context context) {
        super(context);
    }
    public ScrollText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ScrollText(Context context, AttributeSet attrs,
                      int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public boolean isFocused() {
        return true;
    }
}