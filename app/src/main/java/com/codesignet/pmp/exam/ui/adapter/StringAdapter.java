package com.codesignet.pmp.exam.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codesignet.pmp.R;

import java.util.List;

public class StringAdapter extends BaseAdapter {


    @NonNull
    private final Context context;
    @NonNull
    private final List<String> items;

    public StringAdapter(@NonNull Context context, @NonNull List<String> items) {

        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_text_view,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvItem = convertView.findViewById(R.id.tvItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvItem.setText(items.get(position));
        return convertView;
    }

    static class ViewHolder {
        TextView tvItem;
    }
}