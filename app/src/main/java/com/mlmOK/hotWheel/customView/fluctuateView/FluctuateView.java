package com.mlmOK.hotWheel.customView.fluctuateView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mlmOK.hotWheel.utils.UIUtils;
import com.mlmOK.demothree.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩散动画
 *
 * @author molei.li
 * @since 2018/3/22.
 */

public class FluctuateView extends View {

    private Context mContext;
    private Paint paint; //画笔
    private int recleCount = 5; //矩形个数
    private float recleWidth; //每个矩形的宽度
    private float recMaxHeight; //矩形最大高度
    private float recMinHeight; //矩形最小高度
    private float recPadding; //每个矩形的间距
    private List<Pointer> recHeightList; //矩形动态变化时的高度
    private boolean isPlaying; //控制开始/停止
    private int delayTime = 80; //view重绘时间

    private float viewSize = 180f;//80dp

    private final static float DEF_CHANGE_HEIGHT3 = 3f; //每次矩形变化的距离

    public FluctuateView(Context context) {
        super(context);
        this.mContext = context;
        initRes();
        initRecHeightList();
    }

    public FluctuateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initRes();
        initRecHeightList();
    }


    private void initRes() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.rectangleColor));
        paint.setAntiAlias(true);
        recMaxHeight = UIUtils.dip2px(mContext, viewSize * 0.24176f);
        recMinHeight = UIUtils.dip2px(mContext, viewSize * 0.12087f);

        recPadding = UIUtils.dip2px(mContext, viewSize * 0.02747f);
        recleWidth = UIUtils.dip2px(mContext, viewSize * 0.04395f);
    }


    private void initRecHeightList() {
        recHeightList = new ArrayList<>();
        recHeightList.add(new Pointer(recMinHeight));
        recHeightList.add(new Pointer((recMaxHeight + recMinHeight) / 2));
        recHeightList.add(new Pointer(recMaxHeight));
        recHeightList.add(new Pointer((recMaxHeight + recMinHeight) / 2));
        recHeightList.add(new Pointer(recMinHeight));
    }

    private float startX;
    private float startY;
    private float width;
    private float height;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        width = layoutParams.width;
        height = layoutParams.height;
        startX = (width - (recleWidth * recleCount + (recleCount - 1) * recPadding)) / 2;
        startY = height / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isPlaying) {
            return;
        }
        for (int i = 0; i < recleCount; i++) {
            RectF rectF = new RectF();
            float start = Math.abs(startX) + i * recleWidth + i * recPadding;
            rectF.left = start;
            rectF.right = start + recleWidth;
            rectF.top = Math.abs(height) / 2 - recHeightList.get(i).getHeight() / 2;
            rectF.bottom = height / 2 + recHeightList.get(i).getHeight() / 2;
            canvas.drawRoundRect(rectF, recleWidth / 2, recleWidth / 2, paint);
        }
        changeRecHeight();
        postInvalidateDelayed(delayTime);
    }

    /**
     * 改变波动矩形高度List中的值
     */
    private void changeRecHeight() {
        Pointer pointer;
        float height;
        for (int i = 0; i < recHeightList.size(); i++) {
            pointer = recHeightList.get(i);
            if (pointer.isAdding) {
                height = pointer.getHeight() + UIUtils.dip2px(mContext, DEF_CHANGE_HEIGHT3);
                if (height >= recMaxHeight) {
                    height = 2 * recMaxHeight - height;
                    pointer.setAdding(false);
                }
                pointer.setHeight(height);
            } else {
                height = pointer.getHeight() - UIUtils.dip2px(mContext, DEF_CHANGE_HEIGHT3);
                if (height <= recMinHeight) {
                    height = 2 * recMinHeight - height;
                    pointer.setAdding(true);
                }
                pointer.setHeight(height);
            }
        }
    }

    public void start() {
        isPlaying = true;
        postInvalidate();
    }

    public void stop() {
        isPlaying = false;
        recHeightList.clear();
        initRecHeightList();
    }

    class Pointer {
        private float height;

        private boolean isAdding;

        public Pointer(float height, boolean isAdding) {
            this.height = height;
            this.isAdding = isAdding;
        }

        public Pointer(float height) {
            this.height = height;
            this.isAdding = false;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }

        public boolean isAdding() {
            return isAdding;
        }

        public void setAdding(boolean adding) {
            isAdding = adding;
        }
    }


}
