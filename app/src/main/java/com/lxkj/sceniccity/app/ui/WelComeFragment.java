package com.lxkj.sceniccity.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lxkj.sceniccity.R;


/**
 * Created by Slingge on 2017/5/17 0017.
 */

public class WelComeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        int type = getArguments().getInt("type");
        ImageView iv_bg = (ImageView) view.findViewById(R.id.iv_bg);
        if (type == 0) {
            iv_bg.setImageResource(R.drawable.mj_welcome1);
        } else if (type == 1) {
            iv_bg.setImageResource(R.drawable.mj_welcome2);
        }
    }

}
