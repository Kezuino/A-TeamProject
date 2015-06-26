package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.pathfinding.AStarTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

/**
 * @author Ken
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        // Pathfinding package.
        AStarTest.class,
        // Singleplayer package.
        EnemyTest.class,
        GameObjectTest.class,
        GameSessionTest.class,
        ItemTest.class,
        MapTest.class,
        NodeTest.class,
        PactaleTest.class,
        PortalTest.class,
        ProjectileTest.class,
        ScoreTest.class})
public class PactaleTestSuite {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses();
        
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        if (result.wasSuccessful()) {
            System.out.println("All tests in the pactale suite passed.");
        } else {
            System.out.println("TestPactaleConstructor test suite was NOT succesful");
        }
    }
}
