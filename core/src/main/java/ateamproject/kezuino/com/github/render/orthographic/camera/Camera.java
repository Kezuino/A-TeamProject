package ateamproject.kezuino.com.github.render.orthographic.camera;

import ateamproject.kezuino.com.github.singleplayer.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera {

    private final float boundsXLower;
    private final float boundsXUpper;
    private final float boundsYLower;
    private final float boundsYUpper;
    private final float maxZoom;
    private final float minZoom;

    public Camera(float viewportWidth, float viewportHeight, Map map, float scale) {
        super(viewportWidth, viewportHeight);

        this.boundsXLower = -20;
        this.boundsXUpper = map.getWidth() * scale + 20;
        this.boundsYLower = -20;
        this.boundsYUpper = map.getHeight() * scale + 20;
        this.maxZoom = 100f;
        this.minZoom = 0.25f;

        translate(map.getWidth() * scale / 2, map.getHeight() * scale / 2);
        update();
    }


    @Override
    public void update() {
        super.update();
    }
}
