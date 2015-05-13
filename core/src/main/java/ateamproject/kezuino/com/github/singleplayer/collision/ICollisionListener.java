package ateamproject.kezuino.com.github.singleplayer.collision;

interface ICollisionListener {
    /**
     * Executes this action when a collision was detected.
     *
     * @param data Data that explain the collision state.
     */
    void collision(CollisionData data);
}
