/*
 * Copyright 2017 Dongguan UFA network technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.ufa.com
 */
package com.github.phh.benefit.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 精确数字运算
 *
 * @author ufa
 */
public class DigitUtils {
    /**
     * 默认除法运算精度
     */
    private static final int DEF_DIV_SCALE = 4;

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v).setScale(scale, BigDecimal.ROUND_HALF_UP);
        return b.doubleValue();
    }

    /**
     * double比较
     *
     * @param v1
     * @param v2
     * @return -1, 0, or 1 as this {@code BigDecimal} is numerically
     * less than, equal to, or greater than {@code val}.
     */
    public static int compareTo(double v1, double v2) {
        return new BigDecimal(v1).compareTo(new BigDecimal(v2));
    }

    /**
     * long比较
     *
     * @param v1
     * @param v2
     * @return
     */
    public static int compareTo(long v1, long v2) {
        return new BigDecimal(v1).compareTo(new BigDecimal(v2));
    }

    /**
     * 百分比：num1/num2*100
     *
     * @param num1
     * @param num2
     * @return
     */
    public static String percent(double num1, double num2) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format(num1 / num2 * 100);
        return result;
    }

    /**
     * 格式化数字
     *
     * @param d 待格式化的值
     * @param n 小数位数
     * @return
     */
    public static String roundAsString(double d, int n) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留N位小数
        nf.setMaximumFractionDigits(n);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);
        return nf.format(d);
    }

    /**
     * 格式化数字
     *
     * @param value   被格式化对象
     * @param pattern 模版eg. #.00
     * @return
     */
    public static String format(double value, String pattern) {
        return new DecimalFormat(pattern).format(value);
    }

    public static String format(long value, String pattern) {
        return new DecimalFormat(pattern).format(value);
    }

    public static String format(Object value, String pattern) {
        return new DecimalFormat(pattern).format(value);
    }

}
