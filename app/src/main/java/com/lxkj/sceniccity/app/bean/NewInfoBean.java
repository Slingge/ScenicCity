package com.lxkj.sceniccity.app.bean;

import java.util.List;

/**
 * Created by Slingge on 2017/7/4 0004.
 */

public class NewInfoBean {

    public String reason;
    public  resultBean result;


    public class resultBean {

        public List<listBean> list;
        public List<teammatchBean> teammatch;
    }


    public class listBean {

        public List<trBean> tr;
    }


    public class trBean {

        public String time;
        public String player1;
        public String player2;
        public String player1logobig;
        public String player2logobig;
        public String player1url;
        public String player2url;
        public String link1url;//观看
        public String score;

    }

    public class teammatchBean {
        public String name;
        public String url;
    }

}
