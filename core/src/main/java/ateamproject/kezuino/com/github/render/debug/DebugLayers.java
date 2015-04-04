package ateamproject.kezuino.com.github.render.debug;

/**
 * Created by Anton on 4/3/2015.
 */
public enum DebugLayers {
    First(0, null),
    Background(1, null),
    Item(2, ateamproject.kezuino.com.github.singleplayer.Item.class),
    Portal(3, ateamproject.kezuino.com.github.singleplayer.Portal.class),
    GameObject(4, ateamproject.kezuino.com.github.singleplayer.GameObject.class),
    UI(5, null);

    private int order;
    private Class layerClass;
    private boolean visible;

    DebugLayers(int order, Class layerClass) {
        this.order = order;
        this.visible = false;
        this.layerClass = layerClass;
    }

    public Class getLayerClass() {
        return layerClass;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getOrder() {
        return order;
    }
}
