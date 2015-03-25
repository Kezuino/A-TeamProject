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
 * @author Kez and Jules
 */
public class TestGameSession {
    private GameSession testSession;
    private Pactale testPactale;

    @Before
    public void setUp() {
        this.testSession = new GameSession();
        this.testSession.setMap(4);
        this.testPactale = new Pactale(this.testSession.getMap(), 0, 0, 3, 0.1f, Direction.Left, Color.CLEAR);
    }

    /**
     * Tests to retrieve a Pactale playing in the current GameSession
     */
    @Test
    public void testFindPactale() {
        assertSame("The (only) Pactale in the game session isn't the same Pactale added to the map.", this.testSession.getPlayer(0), this.testPactale);
    }
    
    @Test
    public void testGetStartDate() {
        
    }
}
