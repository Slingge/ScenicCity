package com.lxkj.sceniccity.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.sceniccity.R;
import com.lxkj.sceniccity.app.bean.NewInfoBean;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Slingge on 2017/7/4 0004.
 */

public class NewInfoAdapter extends BaseAdapter {

    private Context context;
    private NewInfoBean.resultBean list;

    public NewInfoAdapter(Context context, NewInfoBean.resultBean list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list.data.size() <= 100) {
            return list.data.size();
        } else {
            return 100;
        }
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
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder holder;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_new_info, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) v.findViewById(R.id.image);
            holder.tv_date = (TextView) v.findViewById(R.id.tv_date);
            holder.tv_title = (TextView) v.findViewById(R.id.tv_title);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        ImageLoader.getInstance().displayImage(list.data.get(position).thumbnail_pic_s, holder.image);
        holder.tv_title.setText(list.data.get(position).title);
        holder.tv_date.setText(list.data.get(position).date);

        return v;
    }

    private class ViewHolder {
        ImageView image;
        TextView tv_title, tv_date;

    }
}
