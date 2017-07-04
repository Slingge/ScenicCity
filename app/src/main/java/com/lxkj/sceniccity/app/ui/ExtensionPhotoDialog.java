package com.lxkj.sceniccity.app.ui;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by Slingge on 2017/1/3 0003.
 */

public class ExtensionPhotoDialog {

    private static DisplayImageOptions options;// 展示图片的工具

    public ExtensionPhotoDialog() {
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
    }



}
