package com.github.phh.benefit.common.beans;

import net.sf.cglib.beans.BeanMap;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p> TODO
 *
 * @author phh
 * @version V1.0
 * @project: spring
 * @package com.github.phh.benefit.common.reflect
 * @date 2019/4/17
 */
public class BeanMapUtils {

    private static final Objenesis objenesis = new ObjenesisStd(true);

    /**
     * bean -> map
     *
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        if (bean == null) {
            return new HashMap<>(0);
        }
        BeanMap beanMap = BeanMap.create(bean);
        Set keys = beanMap.keySet();
        Map<String, Object> map = new HashMap<>(keys.size());
        for (Object key : keys) {
            map.put(key.toString(), beanMap.get(key));
        }
        return map;
    }

    /**
     * bean list -> map list
     *
     * @param beanList
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, Object>> beansToMap(List<T> beanList) {
        if (beanList == null || beanList.size() == 0) {
            return new ArrayList<>(0);
        }
        List<Map<String, Object>> mapList = new ArrayList<>(beanList.size());
        for (T bean : beanList) {
            mapList.add(beanToMap(bean));
        }
        return mapList;
    }

    /**
     * map转换为bean
     * 要求map与bean数据类型一致
     *
     * @param map
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * map转换为bean
     * 要求map与bean数据类型一致
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        T bean = objenesis.newInstance(clazz);
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * map list转换为bean list
     * 要求map与bean数据类型一致
     *
     * @param mapList
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> mapToBeans(List<Map<String, Object>> mapList, Class<T> clazz) {
        if (mapList == null || mapList.size() == 0) {
            return new ArrayList<>(0);
        }
        List<T> beanList = new ArrayList<>(mapList.size());
        for (Map<String, Object> map : mapList) {
            beanList.add(mapToBean(map, clazz));
        }
        return beanList;
    }


}
