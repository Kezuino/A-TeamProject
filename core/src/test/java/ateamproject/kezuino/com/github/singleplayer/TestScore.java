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
    }
    
    /**
    *
    */
    @Test
    public void scoreVariable() {
        this.testScore.incrementScore(5);
        assertEquals("Current session score should be equal to 5", 5, this.testScore.getScore());         
        assertEquals("Current session score should be equal to 10", 10, this.testScore.incrementScore(5));
    }
}