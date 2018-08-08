package com.mlmOK.demoThree.bitmapDraw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.mlmOK.demothree.R;

/**
 * @author molei.li
 * @since 2018/3/9.
 */

public class WaterRippleView extends View {

    private boolean mRunning = true;
    private int[] mStrokeWidthArr;
    private int mMaxStrokeWidth;
    private int mRippleCount;
    private int mRippleSpacing;
    private Paint mPaint;
    //    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;

    private View centerView;
    private int center_x;
    private int center_y;
    private int radius;


    public WaterRippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.centerView = centerView;
        initAttrs(context, attrs);
//        setCenterView(centerView);
    }

    public void setCenterView(View ivMic) {
        this.centerView = ivMic;
        center_y = ivMic.getTop() + (ivMic.getBottom() - ivMic.getTop()) / 2;
        center_x = ivMic.getLeft() + (ivMic.getRight() - ivMic.getLeft()) / 2;
        radius = ivMic.getHeight() / 2;
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterRippleView);
        int waveColor = typedArray.getColor(R.styleable.WaterRippleView_rippleColors, ContextCompat.getColor(context, android.R.color.darker_gray));

        mRippleCount = typedArray.getInt(R.styleable.WaterRippleView_rippleCount, 2);
        mRippleSpacing = typedArray.getDimensionPixelSize(R.styleable.WaterRippleView_rippleSpacing, 3);
        mRunning = typedArray.getBoolean(R.styleable.WaterRippleView_rippleAutoRunning, false);

        typedArray.recycle();
        BitmapFactory.Options bOptions = new BitmapFactory.Options();
        bOptions.inScaled = false;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(waveColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = (mRippleCount * mRippleSpacing + centerView.getWidth() / 2) * 2;
        mWidth = resolveSize(size, widthMeasureSpec);
        mHeight = resolveSize(size, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
        mMaxStrokeWidth = (mWidth - centerView.getWidth()) / 2;
        initArray();
    }

    private void initArray() {
        mStrokeWidthArr = new int[mRippleCount];
        for (int i = 0; i < mStrokeWidthArr.length; i++) {
            mStrokeWidthArr[i] = -mMaxStrokeWidth / mRippleCount * i;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mRunning) {
            drawRipple(canvas);
            postInvalidateDelayed(150);
        }
    }


    private void drawRipple(Canvas canvas) {
        for (int strokeWidth : mStrokeWidthArr) {
            if (strokeWidth < 0) {
                continue;
            }
            mPaint.setStrokeWidth(strokeWidth);
            mPaint.setAlpha(255 - 255 * strokeWidth / mMaxStrokeWidth);
            canvas.drawCircle(center_x, center_y, radius * 0.7f, mPaint);
        }
        for (int i = 0; i < mStrokeWidthArr.length; i++) {
            if ((mStrokeWidthArr[i] += 4) > mMaxStrokeWidth) {
                mStrokeWidthArr[i] = 0;
            }
        }
    }

    public void start() {
        mRunning = true;
        postInvalidate();
    }

    public void stop() {
        mRunning = false;
        initArray();
        postInvalidate();
    }
}

