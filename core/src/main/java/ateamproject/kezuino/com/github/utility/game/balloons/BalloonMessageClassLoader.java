package ateamproject.kezuino.com.github.utility.game.balloons;

/**
 * Created by Anton on 6/10/2015.
 */
public class BalloonMessageClassLoader extends ClassLoader {
    private static BalloonMessageClassLoader instance = new BalloonMessageClassLoader();

    public static BalloonMessageClassLoader getInstance() {
        return instance;
    }

    private BalloonMessageClassLoader() {
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println(this.getClass().getCanonicalName());
        return super.loadClass(name);
    }
}
