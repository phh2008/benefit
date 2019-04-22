package com.github.phh.benefit.common;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * <p> TODO
 *
 * @author phh
 * @version V1.0
 * @project: ufa_b6
 * @package com.ufa.mock.test
 * @date 2018/8/6
 */
public class MockTestDemo {

    @Test
    public void test() {
        List mockedList = mock(List.class);

        // using mock object - it does not throw any "unexpected interaction" exception
        mockedList.add("one");
        mockedList.clear();

        // selective, explicit, highly readable verification
        verify(mockedList).add("one");//验证add("one")被调用了:times(1)
        verify(mockedList, times(1)).add("one");//与上面一样
        verify(mockedList).clear();

        // 验证某个交互是否从未被执行
        verify(mockedList, never()).add("two");

    }


    @Test
    public void test2() {

        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        when(mockedList.get(0)).thenReturn("first");

        // the following prints "first"
        System.out.println(mockedList.get(0));

        // the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        doThrow(new NoSuchElementException("e") {
            @Override
            public String getMessage() {
                return "模拟LinkedList.get(666)抛出异常";
            }
        }).when(mockedList).get(666);

        System.out.println("get(555):" + mockedList.get(555));
        System.out.println(mockedList.get(666));
    }

}
