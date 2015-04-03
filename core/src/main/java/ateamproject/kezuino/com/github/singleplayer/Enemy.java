package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.pathfinding.AStar;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
     * The {@link Node} this {@link Enemy} spawned on.
     */
    private Node respawnNode;

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
     * @param map            {@link Map} this {@link Enemy} is currently in.
     * @param x              X position of this {@link Enemy}.
     * @param y              Y position of this {@link Enemy}.
     * @param movementSpeed  Speed in seconds that it takes to move to another {@link Node}.
     * @param direction      Direction this {@link Enemy} is currently facing.
     * @param color          Color of this {@link Enemy}.
     */
    public Enemy(GameObject objectToFollow, Map map, int x, int y, float movementSpeed, Direction direction, Color color) {
        super(map, x, y, movementSpeed, direction, color);
        this.objectToFollow = objectToFollow;       
        this.respawnNode = map.getNode(x, y);
        this.dead = false;
        this.edible = false;
        this.edibleTime = 2f;
        //If an object is followed create path using the aStar pathfinder in the map of the Enemy.
         /*if (objectToFollow!=null){
             graphPath  = new DefaultGraphPath<>();
            map.getPathfinder().searchNodePath(this.getNode(), this.objectToFollow.getNode(), (node1, endNode1) -> 0, graphPath);            
        }*/
    }

    /**
     * Constructs a new {@link Enemy}.
     * The newly constructed {@link Enemy} is not dead.
     * The newly constructed {@link Enemy} is not edible.
     * The default draw color is set to {@code Color.WHITE}.
     *
     * @param objectToFollow {@link GameObject} to follow. Can be null.
     * @param map            {@link Map} that this {@link Enemy} is currently in.
     * @param x              X position of this {@link Enemy}.
     * @param y              Y position of this {@link Enemy}.
     * @param movementSpeed  Speed in seconds that it takes to move to another {@link Node}.
     * @param direction      Direction this {@link Enemy} is currently facing.
     */
    public Enemy(GameObject objectToFollow, Map map, int x, int y, float movementSpeed, Direction direction) {
        this(objectToFollow, map, x, y, movementSpeed, direction, Color.WHITE);
    }

    /**
     * Move an Enemy to its spawn and reset some of its properties.
     */
    public void respawn() {
        setPosition(respawnNode.getX(), respawnNode.getY());
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
     * @param eatable Boolean, cannot be null
     */
    public void setEdible(boolean edible) {
        if(edible) {
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
    public void update() {
        if(this.edible) {
            float secondsFromStart = (System.nanoTime() - this.edibleStartTime) / 1000000000.0f;
            
            if(secondsFromStart >= this.edibleTime) {
                this.edible = false;
                this.setColor(this.previousColor);
            }
        }
        
        //Take the first node out of the created Path, and try to move to it. 
        if (this.graphPath!=null){
            Iterator<Node> nodeFromPath = graphPath.iterator();
            this.move(nodeFromPath.next());
            nodeFromPath.remove();
            graphPath.clear();
            while (nodeFromPath.hasNext()){
                graphPath.add(nodeFromPath.next());
            }
            
           /*for (Node n : nodeFromPath){
              this.move(n);
              break;
           }*/
        }
        
        super.update();
    }   
}