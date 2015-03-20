/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fatih
 */
public class TestWall {
    
    @Test
    public void constructorValidation() {

        GameSession session = new GameSession(10);
        Map map = session.getMap();
        Node node = new Node(map, 1, 1);
        
        /**
	 * Initializes a wall at the position of the given node.
	 * @param node
	 */
        
        Wall wall = new Wall(node);
        
    }
    
    @Test
    public void TestGetPortal() {
        
    }
    
    @Test
    public void TestSetPortal() {
        
    }
    
    @Test
    public void RemovePortal() {
        
    }
    
    @Test
    public void TestGetPortals() {
        
    }
}
