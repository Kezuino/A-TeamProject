/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Fatih
 */
public class TestItem {
    private Node node;    
    private Map map;

    
    @Before
    public void setup() {
        GameSession session = new GameSession(10);
        map = session.getMap();
        node = new Node(map, 1, 1);
    }
    
    @Test
    public void TestItemConstructor() {
        Item item = new Item(node);
        assertNotNull(item);
    }
    
     @Test
    public void setName() {
        
    }
    
     @Test
    public void setOffSetPosition() {
        
    }
    
}
