package com.mlmOK.hotWheel.popWindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * @author mml
 * @since 2018/8/5.
 */

public class BottomListView extends ListView {

    public BottomListView(Context context) {
        super(context);
    }

    public BottomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (canScrollVertically(this)) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(ev);
    }

    public boolean canScrollVertically (AbsListView view) {
        boolean canScroll = false;

        if (view !=null && view.getChildCount ()> 0) {
            boolean isOnTop = view.getFirstVisiblePosition() != 0 || view.getChildAt(0).getTop() != 0;
            boolean isAllItemsVisible = isOnTop && view.getLastVisiblePosition() == view.getChildCount();

            if (isOnTop || isAllItemsVisible) {
                canScroll = true;
            }
        }

        return  canScroll;
    }
}
