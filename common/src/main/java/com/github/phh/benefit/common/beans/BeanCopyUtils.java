package com.github.phh.benefit.common.beans;

import net.sf.cglib.beans.BeanCopier;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    /**
     * 复制集合对象
     *
     * @param srcList 源对象集合
     * @param des     目标Class
     * @param <S>
     * @param <D>
     * @return emptyList if srcList is null or size=0
     */
    public static <S, D> List<D> copyList(List<S> srcList, Class<D> des) {
        if (srcList == null || srcList.size() == 0) {
            return Collections.emptyList();
        }
        BeanCopier copier = getCopier(srcList.get(0).getClass(), des);
        List<D> desList = new ArrayList<>(srcList.size());
        for (S s : srcList) {
            D target = objenesis.newInstance(des);
            copier.copy(s, target, null);
            desList.add(target);
        }
        return desList;
    }

    private static BeanCopier getCopier(Class<?> src, Class<?> des) {
        String key = getCacheKey(src, des);
        return cache.computeIfAbsent(key, k -> BeanCopier.create(src, des, false));
    }

    private static String getCacheKey(Class<?> src, Class<?> des) {
        return src.toString() + des.toString();
    }


}
