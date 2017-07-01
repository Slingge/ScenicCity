package com.lxkj.sunentertainmentcity.app.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lxkj.sunentertainmentcity.R;

import java.util.List;
import java.util.Map;

import static android.R.attr.type;


/**
 * 筹码列表
 * Created by Slingge on 2016/12/29 0029.
 */

public class ChipListAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> list;

    private SparseBooleanArray selected;
    boolean isSingle = true;
    private int old = -1;

    public ChipListAdapter(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
        selected = new SparseBooleanArray();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_chip, parent, false);
            holder = new ViewHolder();
            holder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            holder.tv_chip = (TextView) view.findViewById(R.id.tv_chip);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_chip.setText(list.get(position).get("chip"));

        if (selected.get(position)) {//做判断
            //convertView.setBackgroundResource(R.color.orange);
            holder.checkBox.setChecked(true);
        } else {
            //convertView.setBackgroundResource(R.color.wi);
            holder.checkBox.setChecked(false);
        }
        return view;
    }

    public void setSelectedItem(int selected) {
        if (isSingle = true && old != -1) {
            this.selected.put(old, false);
        }
        this.selected.put(selected, true);
        old = selected;
    }

    class ViewHolder {
        CheckBox checkBox;
        TextView tv_chip;
    }

}
