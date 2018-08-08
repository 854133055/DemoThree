package com.mlmOK.hotWheel.utils;

import android.os.Build;
import android.util.SparseArray;
import android.view.View;

/**
 * @author molei.li
 * @since 2018/3/1.
 */

public class CompatUtil {
    static final int API3 = 3;
    static final int API5 = 5;
    static final int API8 = 8;
    static final int API11 = 11;
    static int version = Build.VERSION.SDK_INT;

    private CompatUtil() {
    }

    public static int getSDKVersion() {
        return version;
    }

    public static boolean hasFroyo() {
        return getSDKVersion() >= 8;
    }

    public static boolean hasGingerbread() {
        return getSDKVersion() >= 9;
    }

    public static boolean hasHoneycomb() {
        return getSDKVersion() >= 11;
    }

    public static boolean hasHoneycombMR1() {
        return getSDKVersion() >= 12;
    }

    public static boolean hasJellyBean() {
        return getSDKVersion() >= 16;
    }

    public static void setObjectToTag(int id, View view, Object obj) {
        if(hasHoneycomb()) {
            view.setTag(id, obj);
        } else {
            Object tag = view.getTag();
            if(!SparseArray.class.isInstance(tag)) {
                tag = new SparseArray();
                view.setTag(tag);
            }

            ((SparseArray)tag).put(id, obj);
            view.setTag(tag);
        }

    }

    public static Object getObjectFromTag(int id, View view) {
        if(hasHoneycomb()) {
            return view.getTag(id);
        } else {
            Object tag = view.getTag();
            return !SparseArray.class.isInstance(tag)?null:((SparseArray)tag).get(id);
        }
    }

}
