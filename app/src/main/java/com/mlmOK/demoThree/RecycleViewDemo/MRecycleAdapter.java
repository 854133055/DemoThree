package com.mlmOK.demoThree.RecycleViewDemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlmOK.demothree.R;


import java.util.List;

/**
 * @author molei.li
 * @since 2018/3/1.
 */

public class MRecycleAdapter extends RecyclerView.Adapter<MRecycleAdapter.MViewHolder> implements View.OnClickListener {

    private Context context;

    private List<Integer> tagList;

    public MRecycleAdapter(Context context, List<Integer> tagList) {
        this.context = context;
        this.tagList = tagList;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.recycle_listview_item, parent, false);
        view.setOnClickListener(this);
        MViewHolder mViewHolder = new MViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {
//        holder.imageView.setImageURI(Uri.parse("res://com.hotWheel.demothree/" + tagList.get(position)));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class MViewHolder extends RecyclerView.ViewHolder {

//        SimpleDraweeView imageView;

        public MViewHolder(View itemView) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.recycle_item);
        }
    }
}
