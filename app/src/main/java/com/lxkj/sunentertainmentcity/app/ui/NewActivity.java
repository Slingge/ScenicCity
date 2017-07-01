package com.lxkj.sunentertainmentcity.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.sunentertainmentcity.R;

/**
 * 显示消息
 * Created by Slingge on 2017/1/6 0006.
 */

public class NewActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        TextView tv_new = (TextView) findViewById(R.id.tv_new);
        tv_new.setText(getIntent().getStringExtra("new"));

        ImageView image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
