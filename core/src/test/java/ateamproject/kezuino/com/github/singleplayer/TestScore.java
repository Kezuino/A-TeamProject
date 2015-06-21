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
        this.testScore = new Score();
        this.testScore.increase(25);
    }
    
    /**
    *
    */
    @Test
    public void testIncrementScore() {
        this.testScore.increase(5);
        assertEquals("Current session score should be equal to 5030", 5030, this.testScore.valueOf());
        assertEquals("Current session score should be equal to 5040", 5040, this.testScore.increase(10));
    }
    
    @Test
    public void testDecrementScore() {
        this.testScore.decrease(5);
        assertEquals("Current session score should be equal to 5020", 5020, this.testScore.valueOf());
        assertEquals("Current session score should be equal to 5010", 5010, this.testScore.decrease(10));
    }
}
