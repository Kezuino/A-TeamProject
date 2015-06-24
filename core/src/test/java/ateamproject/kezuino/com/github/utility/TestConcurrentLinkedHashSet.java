/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.utility;

import ateamproject.kezuino.com.github.utility.collection.ConcurrentLinkedHashSet;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Kez and Jules
 */
public class TestConcurrentLinkedHashSet {
    private ConcurrentLinkedHashSet<Integer> testLinkedHashSet;
    
    @Before
    public void setUp() throws Exception {
        this.testLinkedHashSet = new ConcurrentLinkedHashSet<>();
        
        new Thread(() -> {
            for(int i = 0; i < 200; i++) {
                testLinkedHashSet.add(i);
            }
        }).start();
        
        new Thread(() -> {
            for(int i = 200; i < 1000; i++) {
                testLinkedHashSet.add(i);
            }
        }).start();
        
        new Thread(() -> {
            for(int i = 0; i < 100; i++) {
                testLinkedHashSet.get(i);
            }
        }).start();
        
        new Thread(() -> {
            for(int i = 100; i < 200; i++) {
                testLinkedHashSet.get(i);
            }
        }).start();
    }
    
    @Test
    public void testGet() {
        this.testLinkedHashSet.add(Integer.SIZE);
    }
}
