/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.multiplayer;

import ateamproject.kezuino.com.github.network.rmi.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author Sven
 */
public class ClientTest {

    private Client client;

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
        boolean succeeded = true;

        client = Client.getInstance();
        client.start();
       // client.stop();
        //Als de boolean nog true is, is er geen RemoteException.
        assertTrue(succeeded);
       // client.stop();
    }

}
