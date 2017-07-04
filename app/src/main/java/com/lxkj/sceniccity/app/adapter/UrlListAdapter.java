package com.lxkj.sceniccity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.sceniccity.R;
import com.lxkj.sceniccity.app.ui.WebViewActivity;

import java.util.List;

/**
 * Created by Slingge on 2016/12/30 0030.
 */

public class UrlListAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int windowWidth;

    public UrlListAdapter(Context context, List<String> list, int windowWidth) {
        this.context = context;
        this.list = list;
        this.windowWidth = windowWidth;
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
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_weblist, parent, false);
            holder = new ViewHolder();
            holder.tv_url = (TextView) view.findViewById(R.id.tv_url);
            holder.tv_in = (TextView) view.findViewById(R.id.tv_in);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_url.setText(list.get(position));
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) holder.tv_url.getLayoutParams();
        linearParams.width = windowWidth * 3 / 5;
        holder.tv_url.setLayoutParams(linearParams);

        holder.tv_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", list.get(position));
                context.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHolder {
        TextView tv_url, tv_in;
    }

}
