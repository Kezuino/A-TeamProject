/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import java.lang.reflect.Field;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Kez and Jules
 */
public class TestGameSession {
    private GameSession testSession;
    private Pactale testPactale;
    private Map testMap;
    
    
    @Before
    public void setUp() {
        /*this.testSession = new GameSession("Filepath", 4);
        try {
            Field field = GameSession.class.getDeclaredField("Map");
            field.setAccessible(true);
            this.testMap = (Map) field.get(GameSession.class.getDeclaredField("Map"));
            this.testMap.addGameObject(null, testPactale);
        } catch(NoSuchFieldException | IllegalAccessException ex) {
            
        }*/
        
        
        
        //Field mapField = GameSession
        //Node testNode = new Node(this.session);
        
        //this.testPactale = new Pactale();
        
        
    }
    
    @Test
    public void testFindPactale() {
        //assertNotNull("Map is null", this.testMap);
    }

    
}
