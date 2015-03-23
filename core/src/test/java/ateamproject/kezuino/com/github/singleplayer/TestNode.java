/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Kez and Jules
 */
public class TestNode {
    private GameSession testSession;
    private Node nodeForPactale, nodeforEnemy, nodeForItem;
    private Pactale testPactale;
    private Enemy testEnemy;
    private Item testItem;
    
    @Before
    public void setUp() {
        this.testSession = new GameSession(10 ,10);
        this.nodeForPactale = new Node(this.testSession.getMap(), 1, 5);
        this.nodeforEnemy = new Node(this.testSession.getMap(), 3, 1);
        this.nodeForItem = new Node(this.testSession.getMap(), 0, 8);
    }
    
    /**
    *
    */
    @Test
    public void scoreVariable() {

    }
}
