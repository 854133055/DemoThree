package com.mlmOK.demoThree.bitmapDraw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;


import com.mlmOK.demothree.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * 麦克风波纹动画
 *
 * @author molei.li
 * @since 2018/2/26.
 */

public class CircleWaveView extends View {

    private Context mContext;
    private boolean isStarting = false;//是否运行状态（运行状态：波纹绘制，麦克风显示为监听状况）

    private Paint paint; //波纹paint
    private int maxBorder = 60;//波纹扩散的最大，最小距离
    private int minBorder = 0;
    private int circleBetweenSpace = 20;//波纹的圆之间的间距
    private int circleCountBorder = (maxBorder / circleBetweenSpace) + 1;//波纹删除数量，即可见波纹数
    private int waveColor = 0x00000000;//波纹颜色
    private int circleRadius = 60; //到中心ImageView的距离
    private List<Integer> alphaList = new ArrayList<>();//波纹的透明度List
    private List<Integer> startWidthList = new ArrayList<>();//波纹宽度List


    private int mWidth; //View的宽度
    private int mHeight;//View的高度
    private int IMAGEVIEW_SIZE = 80; //中心ImageView默认Size(dp)
    private int imageViewSize; //中心ImageView的Size
    public static final int IS_LSTENING = 0;
    public static final int NO_LISTEN = 1;
    private Bitmap mBitmap;
    private Drawable islistenDrawable;
    private Drawable noListenDrawable;


    public CircleWaveView(Context context, Context mContext) {
        super(context);
        this.mContext = mContext;
        init(context, null);
    }

    public CircleWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(context, attrs);
    }

    public CircleWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleWaveView);

        int drawableId_0 = typedArray.getResourceId(R.styleable.CircleWaveView_center_view_listening, R.drawable.atom_sv_mic_islisten);
        islistenDrawable = context.getResources().getDrawable(drawableId_0);
        int drawableId_1 = typedArray.getResourceId(R.styleable.CircleWaveView_center_view_nolisten, R.drawable.atom_sv_mic_nolisten);
        noListenDrawable = context.getResources().getDrawable(drawableId_1);

        imageViewSize = typedArray.getDimensionPixelSize(R.styleable.CircleWaveView_center_view_size, IMAGEVIEW_SIZE);
        mBitmap = ((BitmapDrawable) islistenDrawable).getBitmap();
        initWaveView();
    }

    //初始化波纹绘制
    private void initWaveView() {
        paint = new Paint();
        paint.setColor(waveColor); // 设置波纹的颜色
        alphaList.add(maxBorder);// 圆心的透明度
        startWidthList.add(minBorder);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = (maxBorder + circleRadius) * 2;
        mWidth = resolveSize(size, widthMeasureSpec);
        mHeight = resolveSize(size, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.TRANSPARENT);// 颜色：完全透明
        if (isStarting) {
            drawRipple(canvas);
        }
        drawBitmap(canvas);
    }

    // 绘制渐扩波纹。通过绘制同心圆达到这种效果
    private void drawRipple(Canvas canvas) {
        for (int i = 0; i < alphaList.size(); i++) {
            int alpha = alphaList.get(i);
            paint.setAlpha(alpha);
            int startWidth = startWidthList.get(i);// 圆半径
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, startWidth + circleRadius, paint);
            // 同心圆扩散
            if (alpha > minBorder && startWidth < maxBorder) {
                alphaList.set(i, alpha - 1);
                startWidthList.set(i, startWidth + 1);
            }
        }
        if (startWidthList.get(startWidthList.size() - 1) == circleBetweenSpace) {
            alphaList.add(maxBorder);
            startWidthList.add(minBorder);
        }
        // 同心圆数量达到边界数，删除最外层最大的圆
        if (startWidthList.size() == circleCountBorder) {
            startWidthList.remove(0);
            alphaList.remove(0);
        }
        postInvalidateDelayed(5);// 延迟5ms重绘该View
    }

    private void drawBitmap(Canvas canvas) {
        float scale = mBitmap.getWidth() / imageViewSize;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.postScale(1f / scale, 1f / scale);
        Bitmap bitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), scaleMatrix, true);

        int left = (mWidth - bitmap.getWidth()) / 2;
        int top = (mHeight - bitmap.getHeight()) / 2;
        //left and top是相对该View的距离
        canvas.drawBitmap(bitmap, left, top, null);
    }

    // 执行动画
    public void start() {
        isStarting = true;
    }

    // 停止动画,
    public void stop() {
        startWidthList.clear();
        startWidthList.add(minBorder);
        alphaList.clear();
        alphaList.add(maxBorder);
        isStarting = false;
    }

    public boolean isStarting() {
        return isStarting;
    }

    @IntDef({IS_LSTENING, NO_LISTEN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawableState {
    }
}
