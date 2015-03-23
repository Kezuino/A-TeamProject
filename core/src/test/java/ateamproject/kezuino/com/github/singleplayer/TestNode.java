/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Kez and Jules
 */
public class TestNode {
    private GameSession testSession;
    private Node nodeForPactale, nodeforEnemy, nodeForItem, nodeForWall;
    private Pactale testPactale;
    private Enemy testEnemy;
    private Item testItem;
    private Wall testWall;
    
    @Before
    public void setUp() {
        this.testSession = new GameSession(10 ,10);
        
        this.nodeForPactale = new Node(this.testSession.getMap(), 1, 5);
        this.testPactale = new Pactale(this.testSession.getMap(), 1, 5, 3, Color.CLEAR, 0.1f, Direction.Left);

        this.nodeforEnemy = new Node(this.testSession.getMap(), 3, 1);
        this.testEnemy = new Enemy(this.testPactale, this.testSession.getMap(), 3, 1, 0.1f, Direction.Right);
        
        this.nodeForItem = new Node(this.testSession.getMap(), 0, 8);
        this.testItem = new Item(this.nodeForItem);

        this.nodeForWall = new Node(this.testSession.getMap(), 1, 10);
        this.testWall = new Wall(this.nodeForWall);
        //this.nodeForWall.setWall(this.testWall);
    }
    
    /**
    *
    */
    @Test
    public void testGetMap() {
        assertSame("", this.testSession.getMap(), this.nodeForItem.getMap());
        assertSame("", this.testSession.getMap(), this.nodeForPactale.getMap());
        assertSame("", this.testSession.getMap(), this.nodeforEnemy.getMap());
        assertSame("", this.testSession.getMap(), this.nodeForWall.getMap());
    }
    
    /**
     * 
     */
    @Test
    public void testGetWall() {
        assertSame("", this.nodeForWall.getWall(), this.testWall);
    }
    
    /**
     * 
     */
    public void testSetWall() {

    }
    
    /**
     * 
     */
    @Test
    public void testRemoveWall() {
        
    }
    
    /**
     * 
     */
    @Test
    public void testGetItem() {
        assertTrue("", this.nodeForItem.getGameObjects().contains(this.testItem));
    }
    
    /**
     * 
     */
    @Test
    public void testSetItem() {

    }
    
    /**
     * 
     */
    @Test
    public void testRemoveItem() {

    }
    
    /**
     * 
     */
    @Test
    public void testGetX() {
        
    }
    
    /**
     * 
     */
    @Test
    public void testGetY() {
        
    }
    
    /**
     * 
     */
    @Test 
    public void getGameObjects() {
        
    }
    
    /**
     * 
     */
    @Test 
    public void RemoveGameObjects() {
        
    }
    
    /**
     * 
     */
    @Test 
    public void AddGameObjects() {
        
    }
    
    /**
     * 
     */
    @Test 
    public void hasGameObject() {
        
    }
}
