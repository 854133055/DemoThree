package com.mlmOK.demoThree.RecycleViewDemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;


/**
 * @author molei.li
 * @since 2018/3/13.
 */

public class MRecyclerView extends RecyclerView{

    private Context mContext;

    public MRecyclerView(Context context) {
        super(context);
        mContext = context;
    }

    public MRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void mOnClick(MRecycleAdapter adapter) {
        adapter.setOItemClickListener(new MRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
