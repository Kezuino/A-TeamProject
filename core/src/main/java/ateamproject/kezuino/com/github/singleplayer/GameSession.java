package ateamproject.kezuino.com.github.singleplayer;

import java.util.Date;

public class GameSession {
    private Date startTime;
    private String pathToSkin;
    private Map map;
    private Score score;

    private GameSession() {
        startTime = new Date();
    }

    /**
     * Creates a new @see GameSession with the default skin.
     *
     * @param width  X dimension of the @see Map.
     * @param height Y dimension of the @see Map.
     */
    public GameSession(int width, int height) {
        this();
        map = new Map(this, width, height);
    }

    /**
     * Creates a new @see GameSession with the default skin.
     *
     * @param squareSize Size of the map in square root.
     */
    public GameSession(int squareSize) {
        this();
        map = new Map(this, squareSize);
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public String getPathToSkin() {
        return this.pathToSkin;
    }

    public Map getMap() {
        return this.map;
    }

    /**
     * Returns a pactale if found with the specific playerIndex. If not found, null will be returned.
     *
     * @param playerIndex
     */
    public Pactale getPlayer(int playerIndex) {
        // TODO - NOT IN FIRST IMPLEMENTATION.
        throw new UnsupportedOperationException();
    }

}