package com.mlmOK.hotWheel.recycleView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mlmOK.hotWheel.utils.UIUtils;


/**
 * 推荐内容 RecyclerView分割线
 * 目前第一个item前和最后一个item后的dividerHeight和中间部分的不一致
 *
 * @author molei.li
 * @since 2018/3/1.
 */

public class RecycleViewDivider extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private Drawable mDivider;
    private int dividerHeight;//分割线高度
    private int orientation;//列表的方向

    private int otherDividerHeight = 12; //特殊指定的间距, dp

    public RecycleViewDivider(Context context, int mOrientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        orientation = mOrientation;
        dividerHeight = UIUtils.dip2px(context, 0);
        otherDividerHeight = UIUtils.dip2px(context, 0);
    }

    public RecycleViewDivider(Context context, int mOrientation, @DimenRes int mDividerHeight) {
        this(context, LinearLayoutManager.HORIZONTAL);
        dividerHeight = mDividerHeight;
        orientation = mOrientation;
    }

    public RecycleViewDivider(Context context, int mOrientation, @DimenRes int mDividerHeight, int dividerColor) {
        this(context, mOrientation);
        this.dividerHeight = mDividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }


    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //画水平线
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
            //第一项需要 特定的宽度
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.left = otherDividerHeight;
            } else {
                outRect.left = 0;
            }
            outRect.top = 0;
            outRect.bottom = 0;
            outRect.right = dividerHeight;
            //最后一项需要 特定的宽度
            if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
                outRect.right = otherDividerHeight;
            }
        } else {
            //第一项需要 top
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = otherDividerHeight;
            } else {
                outRect.top = 0;
            }
            outRect.bottom = dividerHeight;
            outRect.left = 0;
            outRect.right = 0;
        }
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //画分割线
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin + Math.round(child.getTranslationY());
            final int bottom = top + dividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + dividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            //如果指定了颜色，使用paint进行绘制
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

}
