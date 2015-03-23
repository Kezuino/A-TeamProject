/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Fatih
 */
public class TestPortal {
    
    private Node node;    
    private Map map;

    
    @Before
    public void setup() {
        GameSession session = new GameSession(10);
        map = session.getMap();
        node = new Node(map, 1, 1);
        
        
    }
    
    @Test
    public void TestPortalConstructor() {
     /**
     * Initializes a {@link Portal} from a specific {@link Pactale owner} on a {@link Direction side} of a {@link Wall}.
     *
     * @param owner     That caused this {@link Portal} to be created.
     * @param wall      That contains the {@link Portal} on a side.
     * @param direction Side on the {@link Wall} that this {@link Portal} should appear on.
     */
        
    Wall wall = new Wall(node);
    Pactale pactale = new Pactale(map, 1, 1, 1, Color.CLEAR, 1.1f, Direction.Right);
    Portal p = new Portal(pactale, wall, Direction.Left);
    assertNotNull(p);
        
    }
    
}
