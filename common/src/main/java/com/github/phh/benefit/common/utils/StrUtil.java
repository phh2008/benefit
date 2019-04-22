package com.github.phh.benefit.common.utils;

import java.nio.charset.Charset;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by phh on 2017/7/19.
 */
public class StrUtil {

    private static final char[] HEX_CHAR_TABLE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static final String LETTER_NUMBER = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final Charset UTF8 = Charset.forName("UTF-8");


    /**
     * 获取随机16进制字符串
     *
     * @param len 位数
     * @return
     */
    public static String getHexRandom(int len) {
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < len; i++) {
            sb.append(HEX_CHAR_TABLE[random.nextInt(HEX_CHAR_TABLE.length)]);
        }
        return sb.toString();
    }

    /**
     * 获取随机0-9,a-z,A-Z的字符串
     *
     * @param len 位数
     * @return
     */
    public static String getRandom(int len) {
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < len; i++) {
            sb.append(LETTER_NUMBER.charAt(random.nextInt(LETTER_NUMBER.length())));
        }
        return sb.toString();
    }

    /**
     * 反转字符串
     *
     * @param src
     * @return
     */
    public static String reverse(String src) {
        StringBuilder sb = new StringBuilder();
        int len = src.length();
        for (int i = len - 1; i >= 0; i--) {
            sb.append(src.charAt(i));
        }
        return sb.toString();
    }

    public static byte[] getBytesUtf8(final String string) {
        if (string == null) {
            return null;
        }
        return string.getBytes(UTF8);
    }

    public static String getStringUtf8(final byte[] bytes) {
        return bytes == null ? null : new String(bytes, UTF8);
    }

}
