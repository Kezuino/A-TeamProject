/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.multiplayer;

import ateamproject.kezuino.com.github.PactaleGame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ateamproject.kezuino.com.github.network.rmi.Client;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sven
 */
public class ClientTest {

    private Client client;
    private PactaleGame game = new PactaleGame();

    public ClientTest() {

    }

    @Before
    public void setUp() {
     
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConnectie() {
        boolean gelukt = true;
        
        try {
            client = Client.getInstance(game);
            client.start();
            client.stop();
        } catch (RemoteException ex) {
            gelukt = false;
        }
        //Als de boolean nog true is, is er geen RemoteException.
        assertTrue(gelukt);
        client.stop();
    }

}
