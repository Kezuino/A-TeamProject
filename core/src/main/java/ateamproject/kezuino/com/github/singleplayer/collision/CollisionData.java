package ateamproject.kezuino.com.github.singleplayer.collision;

import ateamproject.kezuino.com.github.singleplayer.GameObject;
import ateamproject.kezuino.com.github.singleplayer.IPositionable;

public class CollisionData {
    protected GameObject owner;
    protected IPositionable target;

    public CollisionData(GameObject owner, IPositionable target) {
        this.owner = owner;
        this.target = target;
    }

    public IPositionable getTarget() {
        return target;
    }

    public GameObject getOwner() {
        return owner;
    }
}
