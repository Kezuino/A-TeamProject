package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class Enemy extends GameObject {

    /**
     * If true, {@link Enemy} is currently dead and doesn't participate in the game until respawned.
     */
    private boolean dead;

    /**
     * If true, {@link Enemy} can be eaten by a {@link Pactale}.
     */
    private boolean edible;

    /**
     * The {@link GameObject} this {@link Enemy} currently follows. Can be null.
     */
    private GameObject objectToFollow;

    /**
     * The {@link Vector2 Position} that this {@link Enemy} will respawn on.
     */
    private Vector2 respawnPosition;

    /**
     * The space to store the start time when the edible state has been set to this {@link Enemy}.
     */
    private float edibleStartTime;

    /**
     * The time this {@link Enemy} will be edible, if set edible.
     */
    private float edibleTime;


    /**
     * The path created by pathfinding
     */
    private GraphPath<Node> graphPath;

    /**
     * Constructs a new {@link Enemy}.
     * The newly constructed {@link Enemy} is not dead.
     * The newly constructed {@link Enemy} is not edible.
     *
     * @param objectToFollow {@link GameObject} to follow. Can be null.
     * @param exactPosition  Absolute position (in pixels) that this {@link Enemy} is on.
     * @param movementSpeed  Speed in seconds that it takes to move to another {@link Node}.
     * @param direction      Direction this {@link Enemy} is currently facing.
     * @param color          Color of this {@link Enemy}.
     */
    public Enemy(GameObject objectToFollow, Vector2 exactPosition, float movementSpeed, Direction direction, Color color) {
        super(exactPosition, movementSpeed, direction, color);
        this.objectToFollow = objectToFollow;
        this.respawnPosition = exactPosition.cpy();
        this.dead = false;
        this.edible = false;
        this.edibleTime = 2f;
        this.graphPath = new DefaultGraphPath<>();
        this.drawOnDirection = false;
    }

    /**
     * Constructs a new {@link Enemy}.
     * The newly constructed {@link Enemy} is not dead.
     * The newly constructed {@link Enemy} is not edible.
     * The default draw color is set to {@code Color.WHITE}.
     *
     * @param objectToFollow {@link GameObject} to follow. Can be null.
     * @param exactPosition  {@link Vector2 Position} that this {@link Enemy} should have.
     * @param movementSpeed  Speed in seconds that it takes to move to another {@link Node}.
     * @param direction      Direction this {@link Enemy} is currently facing.
     */
    public Enemy(GameObject objectToFollow, Vector2 exactPosition, float movementSpeed, Direction direction) {
        this(objectToFollow, exactPosition, movementSpeed, direction, Color.WHITE);
    }

    /**
     * Move an Enemy to its spawn and reset some of its properties.
     */
    public void respawn() {
        setExactPosition(respawnPosition);
    }

    /**
     * Gets the current state of this {@link Enemy).
     * If this enemy is currently dead it will return true, else false.
     *
     * @return The current state of this {@link Enemy}.
     */
    public boolean isDead() {
        return this.dead;
    }

    /**
     * Sets the dead property of the enemy. If the enemy is dead it should be set to true, if the enemy is not dead should be false.
     *
     * @param dead Must be true or false, cannot be null
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Gets wether this {@link Enemy} is edbile.
     *
     * @return True if the enemy is edible at the moment, false if the enemy is not edible.
     */
    public boolean isEdible() {
        return this.edible;
    }

    /**
     * Sets wether this {@link Enemy} is edible.
     * This {@link Enemy} is edible if true.
     * This {@link Enemy} is not edible if false.
     *
     * @param edible Boolean, cannot be null
     */
    public void setEdible(boolean edible) {
        if (edible) {
            this.setColor(Color.WHITE);
            this.edibleStartTime = System.nanoTime();
        }

        this.edible = edible;
    }

    /**
     * @return {@link GameObject} that this {@link Enemy} follows. Can return null.
     */
    public GameObject getObjectToFollow() {
        return this.objectToFollow;
    }

    @Override
    protected boolean collisionWithGameObject(GameObject object) {
        if (object instanceof Pactale) {
            if (this.isEdible()) {
                //this.setDead(true);
                this.getMap().getSession().getScore().incrementScore(500);
                this.setNodePosition(this.getStartingPosition().x, this.getStartingPosition().y);
            }
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if (this.edible) {
            float secondsFromStart = (System.nanoTime() - this.edibleStartTime) / 1000000000.0f;

            if (secondsFromStart >= this.edibleTime) {
                this.edible = false;
                this.setColor(this.previousColor);
            }
        }

        // Set first Pactale as target.
        this.objectToFollow = this.getNode()
                                  .getMap()
                                  .getAllGameObjects()
                                  .stream()
                                  .filter(go -> go instanceof Pactale)
                                  .map(go -> (Pactale) go)
                                  .max((p1, p2) -> (int) p1.getExactPosition()
                                                           .len(p2.getExactPosition().x, p2.getExactPosition().y))
                                  .orElse(null);

        if (!this.isMoving) {
            //If an object is followed create path using the aStar pathfinder in the map of the Enemy.
            if (this.objectToFollow != null) {
                graphPath = this.getMap().getPathfinder().searchNodePath(this.getNode(), this.objectToFollow.getNode());
            }

            //Take the first node out of the created Path, and try to move to it. 
            if (this.graphPath != null && this.graphPath.getCount() > 0) {
                Iterator<Node> nodeFromPath = graphPath.iterator();
                nodeFromPath.next();

                if (nodeFromPath.hasNext()) {
                    Node nextNode = nodeFromPath.next();
                    this.setDirection(Direction.getDirection(this.getNode().getX(), this.getNode()
                                                                                        .getY(), nextNode.getX(), nextNode
                            .getY()));
                    nodeFromPath.remove();
                    graphPath.clear();
                    while (nodeFromPath.hasNext()) {
                        graphPath.add(nodeFromPath.next());
                    }
                }
            }
        }

        super.update();
    }
    
    @Override
    public void destroy() {
        this.graphPath = null;
        this.edible = false;
        this.objectToFollow = null;
        this.respawnPosition = null;
        this.edibleStartTime = 0;
        this.edibleTime = 0;
        
        super.destroy();
    }
}