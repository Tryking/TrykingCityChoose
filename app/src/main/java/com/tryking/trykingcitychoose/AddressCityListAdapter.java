package com.tryking.trykingcitychoose;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 */
public class AddressCityListAdapter extends BaseAdapter {
    private List<CityItemBean> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;
    private ViewHolder holder;

    public AddressCityListAdapter(Context context, List<CityItemBean> datas) {
        mDatas = datas;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = mInflater.inflate(R.layout.address_new_item, parent, false);
//            holder = new ViewHolder();
//            holder.textView = (TextView) convertView.findViewById(R.id.item_text);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        holder.textView.setText(mDatas.get(position).getName());
//        return convertView;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.address_new_item, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.item_text)).setText(mDatas.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}
