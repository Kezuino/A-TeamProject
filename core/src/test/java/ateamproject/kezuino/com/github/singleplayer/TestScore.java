/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package ateamproject.kezuino.com.github.singleplayer;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
* @author Kez and Jules
*/

public class TestScore {
    private GameSession testSession;
    private Score testScore;
    
    @Before
    public void setUp() {
        this.testSession = new GameSession();
        testSession.setMap(4);
        this.testScore = new Score(this.testSession);
        this.testScore.incrementScore(25);
    }
    
    /**
    *
    */
    @Test
    public void testIncrementScore() {
        this.testScore.incrementScore(5);
        assertEquals("Current session score should be equal to 30", 30, this.testScore.getScore());         
        assertEquals("Current session score should be equal to 40", 40, this.testScore.incrementScore(10));
    }
    
    @Test
    public void testDecrementScore() {
        this.testScore.decrementScore(5);
        assertEquals("Current session score should be equal to 20", 20, this.testScore.getScore());
        assertEquals("Current session score should be equal to 10", 10, this.testScore.decrementScore(10));
    }
}