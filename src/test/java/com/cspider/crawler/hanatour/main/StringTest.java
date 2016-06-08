package com.cspider.crawler.hanatour.main;

import org.junit.Test;

public class StringTest {

    @Test
    public void test01(){
        byte bytes[] = new byte[]{49};
        String str = new String(bytes);
        System.out.println(str);
    }

    @Test
    public void test02(){
        byte bytes[] = new byte[]{49, 50};
        String str = new String(bytes);
        System.out.println(str);
    }
}
