package ateamproject.kezuino.com.github.singleplayer;

import java.util.Date;

public class GameSession {
    private Date startTime;
    private String pathToSkin;
    private Map map;
    private Score score;

    /**
     * Creates a new @see GameSession with the default skin.
     */
    public GameSession() {
        startTime = new Date();
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
        //this.map.getAllGameObjects().stream().filter(gameObject -> gameObject instanceof Pactale).filter((Pactale pactale) -> pactale.getPlayerIndex());
        
        
        // TODO - NOT IN FIRST IMPLEMENTATION. SEEN <- OK
        throw new UnsupportedOperationException();
    }

    /**
     * Sets a new {@link Map} for this {@link GameSession}. Connected clients should be synced here if current computer is the host.
     *
     * @param map {@link Map} to change to.
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Sets a new {@link Map} for this {@link GameSession}. Connected clients should be synced here if the current computer is the host.
     *
     * @param width  X dimension of the {@link Map}.
     * @param height Y dimension of the {@link Map}.
     */
    public void setMap(int width, int height) {
        setMap(new Map(this, width, height));
    }

    /**
     * Sets a new {@link Map} for this {@link GameSession}. Connected clients should be synced here if the current computer is the host.
     *
     * @param squareSize X and Y dimension of the {@link Map}.
     */
    public void setMap(int squareSize) {
        setMap(new Map(this, squareSize));
    }
}
