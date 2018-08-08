package com.mlmOK.hotWheel.popWindow;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author mml
 * @since 2018/8/4.
 */

public class UserLocakBottonSheetBehavior extends BottomSheetBehavior {
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent event) {
        return super.onTouchEvent(parent, child, event);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return false;
    }

    public static class BottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_COLLAPSED: //默认的折叠状态
                    Log.e("Bottom Sheet Behaviour", "STATE_COLLAPSED");
                    break;
                case BottomSheetBehavior.STATE_DRAGGING: //过渡状态
                    Log.e("Bottom Sheet Behaviour", "STATE_DRAGGING");
                    break;
                case BottomSheetBehavior.STATE_EXPANDED: //视图从脱离手指自由滑动到最终停下的这一小段时间
                    Log.e("Bottom Sheet Behaviour", "STATE_EXPANDED");
                    break;
                case BottomSheetBehavior.STATE_HIDDEN: // bottom sheet 处于完全展开的状态
                    Log.e("Bottom Sheet Behaviour", "STATE_HIDDEN");
                    break;
                case BottomSheetBehavior.STATE_SETTLING: //默认无此状态
                    Log.e("Bottom Sheet Behaviour", "STATE_SETTLING");
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            Log.d("BottonDialogActivity", "拽中的回调，根据slideOffset可以做一些动画");
        }
    }
}

