package com.github.phh.benefit.common.beans;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> TODO
 *
 * @author phh
 * @version V1.0
 * @project: spring
 * @package com.github.phh.benefit.common.beans
 * @date 2019/4/17
 */
public class BeanMapTest {


    @Test
    public void testBean2Map() {
        BeanC c = new BeanC(false, "tom", 25, 165.6, new Date(), LocalDate.now());
        Map<String, Object> map = BeanMapUtils.beanToMap(c);
        System.out.println(map);
    }

    @Test
    public void testMap2Bean() {
        Map<String, Object> map = new HashMap<>();
        map.put("open", true);
        map.put("birthday", new Date());
        map.put("name", "tom");
        map.put("age", 28);
        map.put("height", 170D);
        map.put("localDate", LocalDate.now());
        BeanC bean = new BeanC();
        BeanMapUtils.mapToBean(map, bean);
        System.out.println(bean);
    }

}
