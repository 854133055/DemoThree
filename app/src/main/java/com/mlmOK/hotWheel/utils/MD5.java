package com.mlmOK.hotWheel.utils;

import java.security.MessageDigest;

/**
 * @author mml
 * @since 2018/8/7.
 */

public class MD5 {

    /**
     * 对字符串进行md5运算
     */
    public static String doCalculateByMD5(String argument) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] inputBytes = argument.getBytes("UTF-8");
            md5.update(inputBytes);
            byte[] resultBytes = md5.digest();

            // 初始化一个字符数组，用来存放每个16进制字符
            char[] hexDigits = "0123456789abcdef".toCharArray();
            // 这个用来组成结果字符串的（一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
            char[] resultCharArray = new char[resultBytes.length * 2];
            // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
            int index = 0;
            for (byte b : resultBytes) {
                resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
                resultCharArray[index++] = hexDigits[b & 0xf];
            }
            return new String(resultCharArray);
        } catch (Exception e) {
            return "";
        }
    }
}
