package com.lxkj.sceniccity.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lxkj.sceniccity.R;
import com.lxkj.sceniccity.app.httpUtil.PreferentialHttp;

/**
 * 优惠
 * Created by Slingge on 2017/1/9 0009.
 */

public class DiscountFragment extends BaseFragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discount, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        new PreferentialHttp().getPreferential(getActivity(), listView);
        return view;
    }

}
