package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.network.packet.packets.PacketObjectMove;
import ateamproject.kezuino.com.github.network.packet.packets.PacketScoreChanged;
import ateamproject.kezuino.com.github.network.rmi.Client;
import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Collectors;

public class Enemy extends GameObject {

    public static Random random = new Random();
    /**
     * If true, this {@link Enemy} won't try to find a path to its {@link #objectToFollow}.
     */
    protected boolean disablePathfinding;
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
    private float timeToChangePath;
    private float timeToChangePathStart;

    /**
     * Empty constructor needed for reflection instantiation.
     */
    public Enemy() {
        isActive = true;
        this.edible = false;
        this.edibleTime = 5f;
    }

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
        this.edibleTime = 5f;
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
        this(objectToFollow, exactPosition, movementSpeed, direction, Color.WHITE.cpy());
    }

    public boolean isDisablePathfinding() {
        return disablePathfinding;
    }

    public void setDisablePathfinding(boolean disablePathfinding) {
        this.disablePathfinding = disablePathfinding;
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
            this.setColor(Color.BLACK);
            this.edibleStartTime = TimeUtils.nanoTime();
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
    public boolean collisionWithGameObject(GameObject object) {
        if (object instanceof Pactale && object.getId().equals(Client.getInstance().getPublicId())) {
            if (this.isEdible()) {
                this.isMoving = false;
                this.setNodePosition(this.getStartingPosition());

                PacketScoreChanged packet = new PacketScoreChanged(500, PacketScoreChanged.ManipulationType.INCREASE, Client.getInstance().getId());
                Client.getInstance().send(packet);
            }
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if (this.edible) {
            float secondsFromStart = (TimeUtils.nanoTime() - this.edibleStartTime) / 1000000000.0f;

            if (secondsFromStart >= this.edibleTime) {
                this.edible = false;
                this.setColor(this.previousColor);
            }
        }

        if(Client.getInstance().isHost()) {
            // Set first Pactale as target.
            
            if(!this.edible) {
                this.objectToFollow = this.getMap()
                    .getAllGameObjects()
                    .stream()
                    .filter(go -> go instanceof Pactale)
                    .min(Comparator.comparing(p1 -> this.getExactPosition().dst(p1.getExactPosition())))
                    .orElse(null);
            } else {
                this.objectToFollow = this.getMap()
                    .getAllGameObjects()
                    .stream()
                    .filter(go -> go instanceof Pactale)
                    .max(Comparator.comparing(p1 -> this.getExactPosition().dst(p1.getExactPosition())))
                    .orElse(null);
            }

            /*System.out.println(this.getMap()
                    .getAllGameObjects()
                    .stream()
                    .filter(go -> go instanceof Pactale)
                    .map(go -> (int)this.getExactPosition().dst(go.getExactPosition()))
                    .min(Integer::min)
                    .get());*/
            
            if (!this.isMoving) {
                //If an object is followed create path using the aStar pathfinder in the map of the Enemy.
                if (!this.disablePathfinding && this.objectToFollow != null) {
                    if (graphPath == null || (TimeUtils.nanoTime() - timeToChangePathStart) / 1000000000.0f >= timeToChangePath) {
                        graphPath = this.getMap()
                                .getPathfinder()
                                .searchNodePath(this.getNode(), this.objectToFollow.getNode());

                        // Notify that a path was generated.
                        /*if (Client.getInstance().isRunning()) {
                            Client.getInstance().send(new PacketSetAIPath(getId(), this.getMap().getPathfinder().pathToVector2(graphPath), getExactPosition(), null));
                        }*/

                        this.timeToChangePath = random.nextFloat() * (.5f - 2f) + .5f;
                        this.timeToChangePathStart = TimeUtils.nanoTime();
                    }
                }

                //Take the first node out of the created Path, and try to move to it. 
                if (this.graphPath != null && this.graphPath.getCount() > 0) {
                    Iterator<Node> nodeFromPath = graphPath.iterator();
                    nodeFromPath.next();

                    if (nodeFromPath.hasNext()) {
                        Node nextNode = nodeFromPath.next();
                        this.setDirection(Direction.getDirection(this.getNode().getX(), this.getNode()
                                .getY(), nextNode.getX(), nextNode.getY()));

                        Client.getInstance().send(new PacketObjectMove(this.getNode().getExactPosition(), nextNode.getExactPosition(), this.getId(), Client.getInstance().getId()));                        
                        nodeFromPath.remove();
                        graphPath.clear();
                        while (nodeFromPath.hasNext()) {
                            graphPath.add(nodeFromPath.next());
                        }
                    }
                }
            }
        }

        super.update();
    }

    /**
     * Sets the {@link GraphPath} that this {@link Enemy} will move towards.
     *
     * @param path {@link Collection} of vector2 that indicate the nodes to follow.
     */
    public void setPath(Collection<Vector2> path) {
        this.graphPath = this.getMap().getPathfinder().vector2ToPath(path);
    }
}