package com.mlmOK.hotWheel.popWindow;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mlmOK.hotWheel.utils.ArrayUtils;
import com.mlmOK.demothree.R;
import com.mlmOK.hotWheel.recycleView.MRecycleView;

import java.util.List;

/**
 * @author mml
 * @since 2018/8/5.
 */

public class BottomPopupWindow extends PopupWindow implements View.OnClickListener{

    private Context context;
    private View vBgBasePicker;
    private LinearLayout llBaseContentPicker;

    public BottomPopupWindow(Context context) {
        super(context);
        this.context = context;
        View view = View.inflate(context, R.layout.picker_base, null);
        vBgBasePicker.setOnClickListener(this);

        /***
         * 添加布局到界面中
         */

        llBaseContentPicker.addView(getContentViews());
        setContentView(view);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setFocusable(true);//设置获取焦点
        setTouchable(true);//设置可以触摸
        setOutsideTouchable(true);//设置外边可以点击
        ColorDrawable dw = new ColorDrawable(0xffffff);
        setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.BottomDialogWindowAnim);

        View contentViews = getContentViews();
        MRecycleView recyclerView = contentViews.findViewById(R.id.atom_sv_bottom_listview);
        BaseAdapter baseListAdapter = new BaseAdapter(context);
        recyclerView.setAdapter(baseListAdapter);
    }

    protected void initData(){
        View contentViews = getContentViews();
        MRecycleView recyclerView = contentViews.findViewById(R.id.atom_sv_bottom_listview);
        BaseAdapter baseListAdapter = new BaseAdapter(context);
        recyclerView.setAdapter(baseListAdapter);
    }


    /**
     * 初始化布局
     *
     * @return
     */
    protected View getContentViews() {
        return LayoutInflater.from(context).inflate(R.layout.fragment_bottom_window,null);
    }

    /**
     * 为了适配7.0系统以上显示问题(显示在控件的底部)
     *
     * @param anchor
     */
    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    /**
     * 展示在屏幕的底部
     *
     * @param layoutid rootview
     */
    public void showAtLocation(@LayoutRes int layoutid) {
        showAtLocation(LayoutInflater.from(context).inflate(layoutid, null),
                Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 最上边视图的点击事件的监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public class BaseAdapter extends RecyclerView.Adapter {

        private Context mContext;
        private List<String> lists;

        public BaseAdapter(Context mContext) {
            this.mContext = mContext;
            this.lists = ArrayUtils.asList("1111111111", "2222222222", "3333333333", "4444444444", "1111111111", "2222222222", "3333333333", "4444444444");
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_bottom_sheet_item, parent, false);
            return new BaseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((BaseViewHolder) holder).textView.setText(lists.get(position));
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }

        class BaseViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            public BaseViewHolder(View itemView) {
                super(itemView);
//                textView = itemView.findViewById(R.id.atom_sv_item_textView);
            }
        }

    }



}
