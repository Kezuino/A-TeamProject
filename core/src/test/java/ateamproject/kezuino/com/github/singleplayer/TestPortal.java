/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Fatih
 */
public class TestPortal {

    private Node node;
    private Map map;

    @Before
    public void setUp() throws Exception {
        GameSession session = new GameSession();
        session.setMap(10);
        map = session.getMap();
        node = new Node(map, 1, 1);
    }

    @Test
    public void testPortalConstructor() {
        /**
         * Initializes a {@link Portal} from a specific {@link Pactale owner} on a {@link Direction side} of a {@link Wall}.
         *
         * @param owner     That caused this {@link Portal} to be created.
         * @param wall      That contains the {@link Portal} on a side.
         * @param direction Side on the {@link Wall} that this {@link Portal} should appear on.
         */

        Pactale pactale = new Pactale(map, 1, 1, 1, 1.1f, Direction.Right, Color.WHITE);
        Portal p = new Portal(pactale, node, Direction.Left);
        assertNotNull("Portal must not be null. It was just created.", p);
        assertNotNull("Pactale must be owner of a portal.", pactale.getPortal());
    }

}
