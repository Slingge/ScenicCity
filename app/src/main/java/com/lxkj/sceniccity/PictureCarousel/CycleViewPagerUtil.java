package com.lxkj.sceniccity.PictureCarousel;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;


import com.lxkj.sceniccity.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 轮播图工具
 * Created by Administrator on 2016/9/29 0029.
 */
public class CycleViewPagerUtil {

    public static Context context;

    public CycleViewPagerUtil(Context context) {
        this.context = context;
    }

    public static void initialize(List<String> list, Fragment fragment) {
        CycleViewPager cycleViewPager;

        cycleViewPager = (CycleViewPager) fragment.getActivity().getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager);


        List<ADInfo> infos = new ArrayList<>();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ADInfo info = new ADInfo();
                info.setUrl(list.get(i));
                infos.add(info);
            }
        } else {
            ADInfo info = new ADInfo();
            info.setUrl("");
            infos.add(info);
        }

        initiali(cycleViewPager, infos, fragment, null);
    }



    public static void initiali(CycleViewPager cycleViewPager, List<ADInfo> infos, Fragment fragment, CycleViewPager.ImageCycleViewListener mAdCycleViewListener) {
        List<ImageView> views = new ArrayList<>();
        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(fragment.getActivity(),
                infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(fragment.getActivity(), infos.get(i)
                    .getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory
                .getImageView(fragment.getActivity(), infos.get(0).getUrl()));
        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);
        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        // 设置轮播
        cycleViewPager.setWheel(true);
        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(3000);
        // 设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

}
