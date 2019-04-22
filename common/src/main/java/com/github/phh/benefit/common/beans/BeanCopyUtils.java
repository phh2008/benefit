package com.github.phh.benefit.common.beans;

import net.sf.cglib.beans.BeanCopier;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.concurrent.ConcurrentHashMap;

/**
 * bean copy by cglib
 *
 * @author phh
 * @version V1.0
 * @date 2019/4/16
 */
public class BeanCopyUtils {

    private static final ConcurrentHashMap<String, BeanCopier> cache = new ConcurrentHashMap<>();

    private static final Objenesis objenesis = new ObjenesisStd(true);

    /**
     * 复制对象属性（类型、名称一致才会复制）
     *
     * @param src 源对象
     * @param des 目标对象
     * @param <S>
     * @param <D>
     * @return
     */
    public static <S, D> D copy(S src, D des) {
        BeanCopier copier = getCopier(src.getClass(), des.getClass());
        copier.copy(src, des, null);
        return des;
    }

    /**
     * 复制对象（类型、名称一致才会复制）
     *
     * @param src 源对象
     * @param des 目标Class
     * @param <S>
     * @param <D>
     * @return
     */
    public static <S, D> D copy(S src, Class<D> des) {
        BeanCopier copier = getCopier(src.getClass(), des);
        D target = objenesis.newInstance(des);
        copier.copy(src, target, null);
        return target;
    }

    private static BeanCopier getCopier(Class<?> src, Class<?> des) {
        String key = getCacheKey(src, des);
        return cache.computeIfAbsent(key, k -> BeanCopier.create(src, des, false));
    }

    private static String getCacheKey(Class<?> src, Class<?> des) {
        return src.toString() + des.toString();
    }


}
