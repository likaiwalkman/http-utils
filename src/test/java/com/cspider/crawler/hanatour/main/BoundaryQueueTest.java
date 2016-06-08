package com.cspider.crawler.hanatour.main;

import org.junit.Assert;
import org.junit.Test;


public class BoundaryQueueTest {

    @Test
    public void test() throws Throwable {
        BoundaryQueue<Integer> q1 = new BoundaryQueue<Integer>(4);
        q1.offer(15);
        q1.offer(15);
        q1.offer(15);
        BoundaryQueue<Integer> q2 = new BoundaryQueue<Integer>(4);
        q2.offer(15);
        q2.offer(15);
        q2.offer(15);
        Assert.assertTrue(q1.equals(q2));
    }
}