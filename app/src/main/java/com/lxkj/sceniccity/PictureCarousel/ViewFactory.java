package com.lxkj.sceniccity.PictureCarousel;

import com.lxkj.sceniccity.R;
import com.lxkj.sceniccity.app.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

    /**
     * 获取ImageView视图的同时加载显示url
     * @return
     */
    public static ImageView getImageView(Context context, String url) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
                R.layout.view_banner, null);
        ImageLoader.getInstance().displayImage(url, imageView, ImageLoaderUtil.getOptions());
        return imageView;
    }
}
