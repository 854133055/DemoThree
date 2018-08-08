package com.mlmOK.hotWheel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import com.mlmOK.demothree.R;

import java.util.List;

/**
 * @author molei.li
 * @since 2018/3/1.
 */

public abstract class HSimpleAdapter<T> extends HArrayAdapter<T> {
    public HSimpleAdapter(Context context, List<T> objects) {
        super(context, objects);
    }

    public HSimpleAdapter(Context context, T[] objects, boolean readOnly) {
        super(context, objects, readOnly);
    }

    public HSimpleAdapter(Context context, T[] objects) {
        super(context, objects);
    }

    public HSimpleAdapter(Context context) {
        super(context);
    }

    public final View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView != null && convertView.getTag(R.id.pub_fw_view_type) != null && convertView.getTag(R.id.pub_fw_view_type).equals(Integer.valueOf(this.getItemViewType(position) + 1))) {
            v = convertView;
        } else {
            v = this.newView(this.mContext, parent);
            v.setTag(R.id.pub_fw_view_type, Integer.valueOf(this.getItemViewType(position) + 1));
        }

        if (position < this.mObjects.size()) {
            this.bindView(v, this.mContext, this.getItem(position), position);
        } else {
            this.bindView(v, this.mContext, null, position);
        }

        return v;
    }

    protected abstract View newView(Context var1, ViewGroup var2);

    protected abstract void bindView(View var1, Context var2, T var3, int postion);
}

