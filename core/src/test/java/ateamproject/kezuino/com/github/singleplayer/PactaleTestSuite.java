package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.pathfinding.TestAStar;
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
        TestAStar.class,
        // Singleplayer package.
        TestEnemy.class,
        TestGameObject.class,
        TestGameSession.class,
        TestItem.class,
        TestMap.class,
        TestNode.class,
        TestPactale.class,
        TestPortal.class,
        TestProjectile.class,
        TestScore.class})
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
