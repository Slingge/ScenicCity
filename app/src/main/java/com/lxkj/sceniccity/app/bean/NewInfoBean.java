package com.lxkj.sceniccity.app.bean;

import java.util.List;

/**
 * Created by Slingge on 2017/7/4 0004.
 */

public class NewInfoBean {

    public String reason;
    public resultBean result;

    public class resultBean {
        public String stat;
        public List<dataBean> data;

    }

    public class dataBean {
        public String title;
        public String thumbnail_pic_s;
        public String url;
        public String date;
    }

}
