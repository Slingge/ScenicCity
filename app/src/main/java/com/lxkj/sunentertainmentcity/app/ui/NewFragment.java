package com.lxkj.sunentertainmentcity.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lxkj.sunentertainmentcity.R;
import com.lxkj.sunentertainmentcity.app.httpUtil.NewHttp;

/**
 * 消息
 * Created by Slingge on 2016/12/30 0030.
 */

public class NewFragment extends BaseFragment {

    private NewHttp newHttp;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);

        newHttp = new NewHttp(getActivity(), listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newHttp.onItem(position);
            }
        });
        newHttp.getNew();
        return view;
    }

}
