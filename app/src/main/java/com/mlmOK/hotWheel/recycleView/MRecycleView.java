package com.mlmOK.hotWheel.recycleView;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.mlmOK.hotWheel.utils.UIUtils;


/**
 * @author molei.li
 * @since 2018/3/7.
 */

public class MRecycleView extends RecyclerView {

    private RecycleViewDivider recycleViewDivider;

    public MRecycleView(Context context) {
        super(context);
    }

    public MRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initDivider(Context context) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        this.setLayoutManager(manager);
        recycleViewDivider = new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, UIUtils.dip2px(getContext(), 1), Color.parseColor("#eeeeee"));
        this.addItemDecoration(recycleViewDivider);
    }
}

