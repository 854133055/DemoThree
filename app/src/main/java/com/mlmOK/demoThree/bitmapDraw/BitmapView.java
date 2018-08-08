package com.mlmOK.demoThree.bitmapDraw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mlmOK.hotWheel.utils.UIUtils;
import com.mlmOK.demothree.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author molei.li
 * @since 2018/3/8.
 */

public class BitmapView extends View {

    private static final float DEFALUT_SIZE = 80;//dp
    private Bitmap bitmap;
    private Drawable drawable1;
    private Drawable drawable2;
    private float drawSize;
    private float scale; /** 缩放比例 */
    public static final int LISTENING = 0;
    public static final int NO_LISTEN = 1;

    public BitmapView(Context context) {
        super(context);
        initView(context, null);
    }

    public BitmapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public BitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BitmapView);
        drawable1 = typedArray.getDrawable(R.styleable.BitmapView_image_res_1);
        drawable2 = typedArray.getDrawable(R.styleable.BitmapView_image_res_2);
        drawSize = typedArray.getDimension(R.styleable.BitmapView_image_size, (float) UIUtils.dip2px(context, DEFALUT_SIZE));
        bitmap = ((BitmapDrawable) drawable1).getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Rect localRect = new Rect(80, 80, 80 + (int) drawSize, 80 + (int) drawSize);
//        Rect srcRect = new Rect(0, 0, (int) drawSize, (int) drawSize);
//        canvas.drawBitmap(bitmap, 0, 0, null);
        scale = DEFALUT_SIZE / drawSize;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap mbitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        canvas.drawBitmap(mbitmap, 0, 0, null);
    }

    public void setDrawableRes(@DrawableState int state) {
        switch (state) {
            case LISTENING:
                bitmap = ((BitmapDrawable) drawable2).getBitmap();
                break;
            case NO_LISTEN:
                bitmap = ((BitmapDrawable) drawable1).getBitmap();
                break;
        }
        invalidate();
    }

    @IntDef({LISTENING, NO_LISTEN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawableState {
    }
}
