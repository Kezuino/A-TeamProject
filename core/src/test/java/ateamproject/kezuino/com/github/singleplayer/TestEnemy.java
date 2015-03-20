/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sven
 */
public class TestEnemy {
    
    public TestEnemy() {
    }   
  
    @Before
    public void setUp() {
        
    }
    
    	/**
	 * Initializes an enemy. Default isDead = False, isEatable = False.
	 * @param pactaleToFollow Pactale object to follow, CAN BE NULL
	 * @param position
	 * @param map
	 * @param spawningpoint
	 * @param parameter
	 * @param movementSpeed
	 * @param walkingDirection
	 * @param color
	 */
    @Test
    public void testConstructorEnemey(){
        
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testWrongConstructor(){
        
    }
    
    /**
         * Sets the dead propertie of the enemy. If the enemy is dead it should be set to true, if the enemy is not dead should be false.
         * @param dead Must be true or false, cannot be null
         */
    @Test
    public void testSetIsDead(){
        
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSetIsDeadNull(){

    }
}

