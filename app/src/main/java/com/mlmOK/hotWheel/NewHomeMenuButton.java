package com.mlmOK.hotWheel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.drawee.view.MultiDraweeHolder;
import com.facebook.imagepipeline.image.ImageInfo;
import com.mlmOK.hotWheel.utils.UIUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 五月版新宫格
 * @author molei.li
 * @since 2018/4/20.
 */

public class NewHomeMenuButton extends View{

    private Context mContext;
    private int defaultBackgroundDraw = -1; //宫格默认图片（包含背景和图片，长度确定按宽高比）
    private String backgroundDraw;
    private String title; //宫格上文案
    private int shadowDrawId = -1; //宫格点击颜色加深
    private String bizName; //业务线唯一标识
    private String textColor = "#FFFFFF"; //标识view所在行，控制文字阴影颜色，第一行，1，第二行，2，第三行，3

    private final String SHADOW_RED = "#BFff4a3c";
    private final String SHADOW_BULE = "#BF00aee7";
    private final String SHADOW_GREEN = "#BF00bd56";

    private int viewType = -1;
    private final int A = 0; //A类型，机酒旅三个View
    private final int B = 1; //B类型，剩下的九个View
    private final double A_ITEM_WIDTH_PERCENT = 0.3106; //A类型View，宽度占宫格内容的百分比
    private final double A_ITEM_RATIO = 2.16; //A类型View宽高比，五月版为1.77
    private final double B_ITEM_WIDTH_PERCENT = 0.2298; //B类型View，宽度占宫格内容的百分比
    private final double B_ITEM_RATIO = 1.60; //B类型View宽高比,五月版为1.311

    private int widthPixels;
    private final double HOMEMENU_PERCENT = 0.9880; //宫格内容部分占屏幕宽度百分比,根据标注计算
    private double HOMEMENU_MARGIN_WIDTH = 9f / 750; //宫格左右margin边距
    private final double HOMEMENU_SPACE_WIDTH = UIUtils.dip2px(mContext, 3); //宫格中间分割线总宽度
    private final double PARENT_SHADOW_WIDTH = UIUtils.dip2px(mContext, 8.6f); //外框阴影的宽度

    private int[] cornerMarkSize;
    private final float CORNER_MARK_HEIGHT_PERCENT = 55f / 750; //角标高度占屏幕宽度百分比

    private double mRatio; //宫格Item宽高比
    private double mWidth;
    private double mHeight;

    private boolean isPressed;
    private String envelopeHotel; //酒店红包代金券文案
    private String envelopeFlight; //机票红包代金券文案
    private String envelopeHoliday; //度假红包代金券文案

    private MultiDraweeHolder<GenericDraweeHierarchy> multiDraweeHolder = new MultiDraweeHolder<>();

    //做缓存的目的是为了避免在draw的时候频繁的访问storage，因为访问storage很耗时，可能产生anr
    private static Map<String, String> cacheBizNameMap = Collections.synchronizedMap(new HashMap<String, String>());
    private static Map<Integer, String> defBizNameSequence;
    private static Map<String, String> defTitleMap;
    private static List<String> bizNameSequence;
    private static String BIZNAME_POS_PREFIX = "bizName";

    public static void putValueToCache(String key, String name) {
        cacheBizNameMap.put(key, name);
//        DataUtils.putPreferences(key, name);
    }

    public String getValueByBizName (String key, String defaultValue){
        return getValueByBizName(key, defaultValue, false);
    }

    public static String getValueByBizName (String key, String defaultValue, boolean isTitle) {
        String name = cacheBizNameMap.get(key);
        if (TextUtils.isEmpty(name)) {
//            name = DataUtils.getPreferences(key, defaultValue);
        }
        if (isTitle && name.length() > 5) {
            name = name.substring(0, 5);
        }
        cacheBizNameMap.put(key, name);
        return name;
    }

    //初始化BizName顺序
//    private static void initBizNameSequence(){
//        if (ArrayUtils.isEmpty(bizNameSequence)) {
//            bizNameSequence = new ArrayList<>();
//            List<String> defBizNameSeq = Arrays.asList(
//                    HomeButtonKey.KEY_HOTEL, HomeButtonKey.KEY_HOTELGROUP, HomeButtonKey.KEY_HOTELINTER, HomeButtonKey.KEY_APART,
//                    HomeButtonKey.KEY_FLIGHT, HomeButtonKey.KEY_TRAIN, HomeButtonKey.KEY_BUS, HomeButtonKey.KEY_RENTCAR,
//                    HomeButtonKey.KEY_VACATION, HomeButtonKey.KEY_VACATIONGROUP, HomeButtonKey.KEY_OUTBOUND, HomeButtonKey.KEY_WALK,
//                    HomeButtonKey.KEY_SIGHT, HomeButtonKey.KEY_ONEDAY ,HomeButtonKey.KEY_AROUND, HomeButtonKey.KEY_GONGLUE
//            );
//            for (int i = 0; i < 16; i++) {
//                bizNameSequence.add(i, getValueByBizName(BIZNAME_POS_PREFIX + i + "", defBizNameSeq.get(i),false));
//            }
//        }
//    }

    //更改bizName顺序
//    public void changeBizNameSeqence(@NonNull int postion, @NonNull String bizName) {
//        if (ArrayUtils.isEmpty(bizNameSequence)) {
//            initBizNameSequence();
//        }
//        bizNameSequence.add(postion, bizName);
//        DataUtils.putPreferences(BIZNAME_POS_PREFIX + postion + "", bizName);
//    }

    //根据bizName获取view位置
//    public static int getPosByBizName(@NonNull String bizName){
//        if (ArrayUtils.isEmpty(bizNameSequence)) {
//            initBizNameSequence();
//        }
//        bizName = bizName.equals(HomeButtonKey.KEY_TICKET) ? HomeButtonKey.KEY_SIGHT : bizName;
//        bizName = bizName.equals(HomeButtonKey.KEY_SURROUNDING) ? HomeButtonKey.KEY_AROUND : bizName;
//        for (int i = 0; i < bizNameSequence.size(); i++) {
//            if (bizName.equals(bizNameSequence.get(i))) {
//                return i;
//            }
//        }
//        return -1;
//    }

//    public static String getBizNameByPos(int pos) {
//        if (pos == -1) {
//            return "";
//        }
//        if (ArrayUtils.isEmpty(bizNameSequence)) {
//            initBizNameSequence();
//        }
//        return bizNameSequence.get(pos);
//    }


    public NewHomeMenuButton(Context context) {
        super(context);
        this.mContext = context;
        this.widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        init();
    }

    public NewHomeMenuButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        init();
    }

    private void init() {
//        initBizNameSequence();
        initHomeMenuData();
        initDrawHolderRes();
    }

    private void initHomeMenuData() {
        String localRes = "res://drawable/";
        if (getTag() == null || Integer.parseInt((String) getTag()) == -1) {
            return;
        }
        switch (Integer.parseInt((String) getTag())) {
            case 0: //酒店,默认数据
                this.viewType = A;
                this.bizName = bizNameSequence.get(0);
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 0 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_RED;
                break;
            case 1: //特价酒店
                this.viewType = B;
                this.bizName = bizNameSequence.get(1);
                this.title = getValueByBizName(bizName + "title", "特价酒店", true);
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 1 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_RED;
                break;
            case 2: //海外酒店
                this.viewType = B;
                this.bizName = bizNameSequence.get(2);
                this.title = getValueByBizName(bizName + "title", "海外酒店", true);
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 2 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_RED;
                break;
            case 3: //民宿·客栈
                this.viewType = B;
                this.bizName = bizNameSequence.get(3);
                this.title = getValueByBizName(bizName + "title", "民宿·客栈", true);
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 3 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_RED;
                break;
            case 4: //机票
                this.viewType = A;
                this.bizName = bizNameSequence.get(4);
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 4 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_BULE;
                break;
            case 5: //火车票
                this.viewType = B;
                this.bizName = bizNameSequence.get(5);
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 5 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_BULE;
                break;
            case 6: //汽车·船票
                this.viewType = B;
                this.bizName = bizNameSequence.get(6);
                this.title = getValueByBizName(bizName + "title", "汽车·船票", true);
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 6 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_BULE;
                break;
            case 7: //专车·租车
                this.viewType = B;
                this.bizName = bizNameSequence.get(7);
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 7 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_BULE;
                break;
            case 8: //旅游
                this.viewType = A;
                this.bizName = bizNameSequence.get(8);
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 8 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_GREEN;
                break;
            case 9: //旅游团购
                this.bizName = bizNameSequence.get(9);
                this.viewType = B;
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 9 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_GREEN;
                break;
            case 10: //出境游
                this.bizName = bizNameSequence.get(10);
                this.viewType = B;
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 10 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_GREEN;
                break;
            case 11://自由行
                this.bizName = bizNameSequence.get(11);
                this.viewType = B;
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 11 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_GREEN;
                break;
            case 12: //景点·门票
                this.bizName = bizNameSequence.get(12);
                this.viewType = A;
                this.title = getValueByBizName(bizName + "title", "景点门票", true);
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 12 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_GREEN;
                break;
            case 13: //一日游
                this.bizName = bizNameSequence.get(13);
                this.viewType = B;
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 13 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_GREEN;
                break;
            case 14: //周边游
                this.bizName = bizNameSequence.get(14);
                this.viewType = B;
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 14 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_GREEN;
                break;
            case 15: //攻略
                this.bizName = bizNameSequence.get(15);
                this.viewType = B;
                this.backgroundDraw = getValueByBizName("homeMenuPos"+ 15 + "icon", localRes + defaultBackgroundDraw);
                textColor = SHADOW_GREEN;
                break;
        }
    }


    private void initDrawHolderRes() {
        int fadeDuration = 0;
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(mContext.getResources());
        builder.setFadeDuration(fadeDuration);
        //最底层图片
        GenericDraweeHierarchy hierarchy = builder.build();
        DraweeHolder draweeHolder;
        if (defaultBackgroundDraw != -1) {
            hierarchy.setPlaceholderImage(mContext.getResources().getDrawable(defaultBackgroundDraw), ScalingUtils.ScaleType.FIT_CENTER);
            draweeHolder = DraweeHolder.create(hierarchy, mContext);
            draweeHolder.getTopLevelDrawable().setDither(true);
            multiDraweeHolder.add(draweeHolder);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(backgroundDraw))
                    .setOldController(multiDraweeHolder.get(0).getController())
                    .build();
            multiDraweeHolder.get(0).setController(controller);
        }
        //条幅或角标
        hierarchy = builder.build();
        draweeHolder = DraweeHolder.create(hierarchy, mContext);
        draweeHolder.getTopLevelDrawable().setDither(true);
        multiDraweeHolder.add(draweeHolder);
        //点击图
        if (shadowDrawId != -1) {
            hierarchy = builder.build();
            hierarchy.setPlaceholderImage(mContext.getResources().getDrawable(shadowDrawId), ScalingUtils.ScaleType.FIT_CENTER);
            draweeHolder = DraweeHolder.create(hierarchy, mContext);
            draweeHolder.getTopLevelDrawable().setDither(true);
            multiDraweeHolder.add(draweeHolder);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //宫格两种样式A，B
        if (viewType != -1) {
            switch (viewType) {
                case A:
                    mRatio = A_ITEM_RATIO;
                    mWidth = (widthPixels * HOMEMENU_PERCENT - HOMEMENU_SPACE_WIDTH - PARENT_SHADOW_WIDTH * 2) * A_ITEM_WIDTH_PERCENT;
                    mHeight = mWidth / mRatio;
                    break;
                case B:
                    mRatio = B_ITEM_RATIO;
                    mWidth = (widthPixels * HOMEMENU_PERCENT - HOMEMENU_SPACE_WIDTH - PARENT_SHADOW_WIDTH * 2) * B_ITEM_WIDTH_PERCENT;
                    mHeight = mWidth / mRatio;
                    break;
            }
        }
        setMeasuredDimension((int) mWidth, (int) mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制底层背景图
        if (defaultBackgroundDraw != -1) {
            Drawable backgroundDrawable = multiDraweeHolder.get(0).getHierarchy().getTopLevelDrawable();
            backgroundDrawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
            backgroundDrawable.draw(canvas);
        }
        //绘制文字,分为两种，A：上下居中距左边距28px，占屏幕的3.733%，B位位于正中心（文字大小占屏幕4%）
        if (!TextUtils.isEmpty(title) && viewType != -1) {
            Paint textPaint = new Paint();
            textPaint.setColor(0xffffffff);
            textPaint.setShadowLayer(UIUtils.dip2px(mContext, 1), UIUtils.dip2px(mContext, 1), UIUtils.dip2px(mContext, 1), Color.parseColor(textColor));
            textPaint.setTextSize(getResources().getDisplayMetrics().widthPixels * 0.04f);
            Paint.FontMetricsInt metricsInt = textPaint.getFontMetricsInt();
            int baseline = (this.getBottom() + this.getTop() - metricsInt.bottom - metricsInt.top) / 2;
            switch (viewType) {
                case A:
                    canvas.drawText(title, getLeft() + widthPixels * 0.03733f, baseline, textPaint);
                    break;
                case B:
                    textPaint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(title, (this.getRight() + this.getLeft()) / 2, baseline, textPaint);
            }
        }
        //角标
        if (cornerMarkSize != null) {
            Drawable cornerMark = multiDraweeHolder.get(1).getHierarchy().getTopLevelDrawable();
            cornerMark.setBounds(0, 0, cornerMarkSize[1], cornerMarkSize[0]);
            cornerMark.draw(canvas);
        }
        //条幅，是动态添加的逻辑在MainActivity

        //点击时额外绘制一层蒙板
        if (isPressed) {
            Drawable pressedDrawable = multiDraweeHolder.get(2).getHierarchy().getTopLevelDrawable();
            pressedDrawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
            pressedDrawable.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isPressed = true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                boolean needPerformClick = isPressed;
                isPressed = false;
                invalidate();
                if (needPerformClick) {
                    performClick();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!pointInView(event.getX(), event.getY(), UIUtils.dip2px(mContext, 10))) {
                    isPressed = false;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                isPressed = false;
                invalidate();
                break;
        }
        return true;
    }

    private boolean pointInView(float localX, float localY, float slop) {
        return localX >= -slop && localY >= -slop && localX < getWidth() + slop && localY < getHeight() + slop;
    }

    //设置宫格边距，有小黑条时需要额外处理
    public void setHomeMenuMargin(View parent, boolean isExistOrderingWaring) {
        if (parent == null) return;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) parent.getLayoutParams();
        int margin = (int) (widthPixels * HOMEMENU_MARGIN_WIDTH);
        if (isExistOrderingWaring){
            params.setMargins(margin, 0, margin, 0); //有小黑条情况下不留marginTop
        } else {
            params.setMargins(margin, margin, margin, 0);
        }
        parent.setLayoutParams(params);
    }

    public int getSmallEntranceMargin(int px) {
        float percent = px / 750f;
        return (int) (widthPixels * percent);
    }

    public void updateHomeMenuData() {
        initHomeMenuData();
        postInvalidate();
    }

    public void updateBackgroundImage(String icon) {
        if (TextUtils.isEmpty(icon)) {
            return;
        }
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(icon))
                .setControllerListener(new BaseControllerListener<ImageInfo>(){
                       @Override
                       public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                           super.onFinalImageSet(id, imageInfo, animatable);
                           postInvalidate();
                       }
                   })
                .setOldController(multiDraweeHolder.get(0).getController())
                .build();
        multiDraweeHolder.get(0).setController(controller);
    }

    public void updateIconImage(final String url, final NewHomeMenuButton mod, final boolean isBigBtn) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(Uri.parse(url)).setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                cornerMarkSize = null;
            }

            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                cornerMarkSize = new int[2];
                if (mod == null) {
                    return;
                }
                //确定角标宽高
                cornerMarkSize[0] = (int) (widthPixels * CORNER_MARK_HEIGHT_PERCENT); //高度
                cornerMarkSize[1] = cornerMarkSize[0] * (imageInfo.getWidth() / imageInfo.getHeight()); //宽度
                postInvalidate();
            }
        }).setOldController(multiDraweeHolder.get(1).getController()).build();
        multiDraweeHolder.get(1).setController(controller);
    }

    //参数校验，如果bizName为空，type类型不匹配，则使用默认数据
//    public static boolean isIllegalParameter(List<HomeMenuCResult.HomeSwitchItem> list){
//        for (HomeMenuCResult.HomeSwitchItem item : list) {
//            if (TextUtils.isEmpty(item.bizName) || (item.type != 1 && item.type != 2 && item.type != 3)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public void removeIconImage() {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        // set fade duration
        builder.setFadeDuration(0);
        DraweeHolder mDraweeHolder = DraweeHolder.create(builder.build(), getContext());
        multiDraweeHolder.remove(1);
        multiDraweeHolder.add(1, mDraweeHolder);
    }


    public void updateEnvelopeHotel(String envelopeNoteHotel) {
        if (TextUtils.isEmpty(envelopeNoteHotel) || envelopeNoteHotel.equals(envelopeHotel)){ return; }
        envelopeHotel = envelopeNoteHotel;
        invalidate();
    }


    public void updateEnvelopeFlight(String voucher) {
        if (TextUtils.isEmpty(voucher) || voucher.equals(envelopeFlight)){ return; }
        envelopeFlight = voucher;
        invalidate();
    }

    public void updateEnvelopeHoliday(String envelopeNoteHoliday) {
        if (TextUtils.isEmpty(envelopeNoteHoliday) || envelopeNoteHoliday.equals(envelopeHoliday)){ return; }
        envelopeHoliday = envelopeNoteHoliday;
        invalidate();
    }

    public void setBmHolidayPerferential(String adrImgUrl, Runnable runnable) {
        //五月版度假宫格不支持活动图片，只支持文字展示
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        multiDraweeHolder.onDetach();
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        multiDraweeHolder.onDetach();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        multiDraweeHolder.onAttach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        multiDraweeHolder.onAttach();
    }
}
