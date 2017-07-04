package com.lxkj.sceniccity.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxkj.sceniccity.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Slingge on 2016/12/30 0030.
 */

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> list;

    public GridViewAdapter(Context context, List<Map<String, String>> list) {
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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_gridview, parent, false);
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.text.setText(list.get(position).get("id"));

        return view;
    }

    class ViewHolder {
        TextView text;
    }

}
