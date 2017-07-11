package com.lxkj.sceniccity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.sceniccity.R;
import com.lxkj.sceniccity.app.bean.NewInfoBean;
import com.lxkj.sceniccity.app.ui.WebViewActivity;
import com.lxkj.sceniccity.app.ui.newInfo.NewsInfoActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import static com.lxkj.sceniccity.R.id.tv_score;

/**
 * Created by Slingge on 2017/7/4 0004.
 */

public class NewInfoAdapter extends BaseAdapter {

    private Context context;
    private List<NewInfoBean.trBean> list;

    public NewInfoAdapter(Context context, List<NewInfoBean.trBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        ViewHolder holder;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_new_info, parent, false);
            holder = new ViewHolder();
            holder.image1 = (ImageView) v.findViewById(R.id.image1);
            holder.image2 = (ImageView) v.findViewById(R.id.image2);
            holder.tv_title1 = (TextView) v.findViewById(R.id.tv_title1);
            holder.tv_title2 = (TextView) v.findViewById(R.id.tv_title2);
            holder.tv_content1 = (TextView) v.findViewById(R.id.tv_content1);
            holder.tv_content2 = (TextView) v.findViewById(R.id.tv_content2);
            holder.tv_score = (TextView) v.findViewById(tv_score);
            holder.tv_watch = (TextView) v.findViewById(R.id.tv_watch);
            holder.tv_date = (TextView) v.findViewById(R.id.tv_date);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        ImageLoader.getInstance().displayImage(list.get(position).player1logobig, holder.image1);
        ImageLoader.getInstance().displayImage(list.get(position).player2logobig, holder.image2);

        holder.tv_title1.setText(list.get(position).player1);
        holder.tv_title2.setText(list.get(position).player2);

        holder.tv_score.setText(list.get(position).score);
        holder.tv_date.setText(list.get(position).time);

        holder.tv_content1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", list.get(position).player1url);
                intent.putExtra("flag", 0);
                context.startActivity(intent);
            }
        });
        holder.tv_content2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", list.get(position).player2url);
                intent.putExtra("flag", 0);
                context.startActivity(intent);
            }
        });
        holder.tv_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", list.get(position).link1url);
                intent.putExtra("flag", 0);
                context.startActivity(intent);
            }
        });
        return v;
    }

    private class ViewHolder {
        ImageView image1, image2;
        TextView tv_title1, tv_title2, tv_content1, tv_content2;
        TextView tv_score, tv_watch, tv_date;

    }
}
