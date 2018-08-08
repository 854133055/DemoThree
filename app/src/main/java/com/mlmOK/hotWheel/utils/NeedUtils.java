package com.mlmOK.hotWheel.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author mml
 * @since 2018/8/7.
 */

public class NeedUtils {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    /**
     * 读取本地文件
     */
    public static String readFromAssets(Context mContext, String fileName) {
        try (InputStream inputStream = mContext.getAssets().open(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException e) {
            Log.e("atom_sv", "加载本地base.json出错" + e);
        }
        return "";
    }

    /**
     * 获取设备IP
     */
    public static String getIpAddress(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) { //使用移动网络
                try {
                    for (Enumeration<NetworkInterface> enumIpAddr = NetworkInterface.getNetworkInterfaces(); enumIpAddr.hasMoreElements(); ) {
                        NetworkInterface netInt = enumIpAddr.nextElement();
                        for (Enumeration<InetAddress> enumeIpAddr = netInt.getInetAddresses(); enumeIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumeIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }

                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) { //使用Wifi
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                return getStringIPAddr(wifiInfo.getIpAddress());
            }
        } else { //当前没有网络
            return "";
        }
        return null;
    }

    private static String getStringIPAddr(int ipInt) {
        StringBuilder builder = new StringBuilder();
        builder.append((ipInt & 0xFF)).append(".").append((ipInt >> 8) & 0xFF).append(".")
                .append((ipInt >> 16) & 0xFF).append(".").append(ipInt >> 24 & 0xFF);
        return builder.toString();
    }

    /**
     * 检查当前手机是否有虚拟导航按键
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        try {
            Resources rs = context.getResources();
            int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id);
            }
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            Log.e("atom_sv", "checkDeviceHasNavigationBar失败" + e);
        }
        return hasNavigationBar;
    }

    /**
     * iconContent：f0ed-> \uf0ed
     * @param iconfont
     */
    public static String stringToUnicode(String iconfont) {
        String a = "\\u" + iconfont;
        StringBuilder string = new StringBuilder();
        String[] hex = a.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            string.append((char) data);
        }
        return string.toString();
    }

    /**
     * 获取文字高度
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static int getTvHeight(TextView tv, String source, int width){
        StaticLayout staticLayout = new StaticLayout(source, tv.getPaint(), width,
                Layout.Alignment.ALIGN_NORMAL, tv.getLineSpacingMultiplier(), 0f, true);
        return staticLayout.getHeight();
    }

    /**
     * 获取文字行数
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static int getTvLineCount(TextView tv, String source, int width){
        StaticLayout staticLayout = new StaticLayout(source, tv.getPaint(), width,
                Layout.Alignment.ALIGN_NORMAL, tv.getLineSpacingMultiplier(), 0f, true);
        return staticLayout.getLineCount();
    }
}
