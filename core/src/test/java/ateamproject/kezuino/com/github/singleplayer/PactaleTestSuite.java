package ateamproject.kezuino.com.github.singleplayer;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

/**
 *
 * @author Ken
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestEnemy.class,
    TestGameObject.class,
    TestGameSession.class,
    TestItem.class,
    TestMap.class,
    TestNode.class,
    TestPortal.class,
    TestScore.class,
    TestPactale.class,
    TestProjectile.class})
public class PactaleTestSuite {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(PactaleTestSuite.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("All tests in the fontys.schedule suite passed.");
        } else {
            System.out.println("Schedule test suite was NOT succesful");
        }
    }
}
