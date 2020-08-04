package com.demo.popupwindowdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class AdapterBase<T> extends BaseAdapter {

    protected List<T> mList;
    protected Context mContext;
    protected LayoutInflater mInflater;

    public AdapterBase(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = new ArrayList<T>();
    }

    public AdapterBase(Context context, List<T> list) {
        mContext = context;
        mList = list;
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 获取adapter里面的所有数据
     *
     * @return
     */
    public List<T> getList() {
        return mList;
    }

    /**
     * 往adapter里面填充数据
     *
     * @param list
     */
    public void setList(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    /**
     * 往adapter里面追加数据（往后面追加）
     *
     * @param list
     */
    public void appendToList(List<T> list) {
        if (list != null) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    /**
     * 往adapter里面追加数据（往前面追加）
     *
     * @param list
     */
    public void appendToTopList(List<T> list) {
        if (list != null) {
            mList.addAll(0, list);
            notifyDataSetChanged();
        }
    }

    /**
     * 清空所有列表数据并且刷新adapter
     */
    public void clear() {
        if (mList != null) {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position > mList.size() - 1) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
