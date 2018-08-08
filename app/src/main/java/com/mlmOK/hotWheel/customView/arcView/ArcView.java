package com.mlmOK.hotWheel.customView.arcView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author molei.li
 * @since 2018/4/3.
 */

public class ArcView extends View {

    private Paint arcPaint;
    private int arcColor = Color.parseColor("#000000");

    public ArcView(Context context) {
        super(context);
        initArgument();
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initArgument();
    }

    private void initArgument() {
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setColor(arcColor);
    }

    private int widthSize;
    private int heightSize;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.moveTo(0, heightSize);
        path.quadTo(widthSize / 2, -heightSize, widthSize, heightSize);
        canvas.drawPath(path, arcPaint);
    }
}
