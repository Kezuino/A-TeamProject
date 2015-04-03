package ateamproject.kezuino.com.github.render;

public interface IRenderer {

    /**
     * Called when this {@link ateamproject.kezuino.com.github.render.IRenderer} should be shown.
     */
    public void active();

    /**
     * Called for each requested frame by the gameloop.
     */
    public void render();
}
