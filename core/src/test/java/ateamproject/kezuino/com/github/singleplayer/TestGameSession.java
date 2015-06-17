/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author Kez and Jules
 */
public class TestGameSession {
    private GameSession testSession;
    private Pactale testPactale;

    @Before
    public void setUp() {
        this.testSession = new GameSession(1);
        this.testSession.setMap(4);
        this.testPactale = new Pactale(Vector2.Zero, 3, 0.1f, Direction.Left, Color.WHITE);
        this.testSession.getMap().addGameObject(testPactale);
    }

    /**
     * @deprecated Cannot test this method yet. TODO in next iteration..
     */
    @Deprecated
    public void testFindPactale() {
        // Tests to retrieve a TestPactaleConstructor playing in the current GameSession.
        assertSame("The (only) TestPactaleConstructor in the game session isn't the same TestPactaleConstructor added to the map.", this.testSession.getPlayer(0), this.testPactale);
    }

    @Test
    public void testGetStartDate() throws InterruptedException {
        // Delay execution so there is a time difference.
        Thread.sleep(10);
        Date currentDate = new Date();
        assertTrue("Started date must be earlier than the current date.", currentDate.after(testSession.getStartTime()));
    }
}
