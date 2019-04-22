package com.github.phh.benefit.common.beans;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p> TODO
 *
 * @author phh
 * @version V1.0
 * @project: spring
 * @package com.github.phh.benefit.common.beans
 * @date 2019/4/17
 */
public class BeanCopyTest {

    @Test
    public void test() {
        BeanA a = new BeanA(true, "tom", 23, 165.5, new Date(), "男", "", BeanA.Op.B, LocalDate.of(2018, 5, 12), LocalDateTime.now());
        System.out.println(a);
        BeanB b = BeanCopyUtils.copy(a, BeanB.class);
        System.out.println(b);
    }

    @Test
    public void test2() {
        BeanA a = new BeanA(true, "tom", 23, 165.5, new Date(), "男", "", BeanA.Op.B, LocalDate.of(2018, 5, 12), LocalDateTime.now());
        System.out.println(a);
        BeanB b = new BeanB();
        BeanCopyUtils.copy(a, b);
        System.out.println(b);
    }

}
