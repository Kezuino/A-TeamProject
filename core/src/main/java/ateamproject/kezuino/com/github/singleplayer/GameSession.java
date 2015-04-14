package ateamproject.kezuino.com.github.singleplayer;

import java.util.Date;

public class GameSession {
    private Date startTime;
    private Map map;
    private Score score;

    /**
     * Creates a new @see GameSession with the default skin.
     */
    public GameSession() {
        startTime = new Date();
        this.score = new Score(this);
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public Map getMap() {
        return this.map;
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
     * @param squareSize X and Y dimension of the {@link Map}.
     */
    public void setMap(int squareSize) {
        setMap(new Map(this, squareSize));
    }
    
    public void setScore(Score score) {
        if(score != null) {
            this.score = new Score(this, score.valueOf());
        }
    }

    /**
     * Returns a {@link Pactale} if found with the specific {@code playerIndex}. If not found, null will be returned.
     *
     * @param playerIndex
     */
    public Pactale getPlayer(int playerIndex) {
        return this.map.getAllGameObjects()
                       .stream()
                       .filter(gameObject -> gameObject instanceof Pactale)
                       .map(gameObject -> (Pactale) gameObject)
                       .filter((Pactale pactale) -> pactale.getPlayerIndex() == playerIndex)
                       .findFirst()
                       .orElse(null);
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
     * Gets the current game {@link Score} this {@link GameSession} is applied to.
     *
     * @return The current {@link Score} from this {@link GameSession}.
     */
    public Score getScore() {
        return this.score;
    }
}
