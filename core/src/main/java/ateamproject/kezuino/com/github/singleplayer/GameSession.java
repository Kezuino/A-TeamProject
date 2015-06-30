package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.network.packet.packets.PacketHighScore;
import java.util.Date;
import java.util.UUID;

public class GameSession {

    private final Date startTime;
    /**
     * Used for lobby hosting. Counts the amount of objects received from the
     * host. If count is equal, all objects were received and game can start.
     */
    private int objectsLoaded;
    /**
     * Total amount of objects the
     * {@link ateamproject.kezuino.com.github.network.Client} should load.
     */
    private int objectsToLoad;
    private Map map;
    private Score score;
    private GameState state;
    private boolean showingPlayerMenu;
    private boolean showingPauseMenu;
    /**
     * Whether this game is synchronizing with a server.
     */
    private boolean isMultiplayer;
    private int level;

    /**
     * Creates a new @see GameSession with the default skin.
     */
    public GameSession(int level) {
        this.showingPlayerMenu = false;
        this.showingPauseMenu = false;
        startTime = new Date();
        this.score = new Score();
        this.state = GameState.Running;
        this.level = level;
    }

    public int getObjectsToLoad() {
        return objectsToLoad;
    }

    public void setObjectsToLoad(int objectsToLoad) {
        this.objectsToLoad = objectsToLoad;
    }

    public int getObjectsLoaded() {
        return objectsLoaded;
    }

    public void setObjectsLoaded(int objectsLoaded) {
        this.objectsLoaded = objectsLoaded;
    }

    /**
     * Gets the {@link GameState} in which this {@link GameSession} is currently
     * in.
     *
     * @return {@link GameState} in which this {@link GameSession} is currently
     * in.
     */
    public GameState getState() {
        return this.state;
    }

    public void pause() {
        if (!this.hasEnded()) {
            this.state = GameState.Paused;
        }
    }

    public void resume() {
        if (!this.hasEnded()) {
            this.state = GameState.Running;
        }
    }

    public void complete() {
        if (!this.hasEnded()) {
            this.state = GameState.Completed;
        }
    }

    public void end() {
        this.state = GameState.Ended;
    }

    public void gameOver() {
        if (!this.hasEnded()) {
            this.state = GameState.GameOver;
        }

        if (false) {//TODO: if clan
            PacketHighScore packet = new PacketHighScore("jos", this.score.valueOf(),null,null);
            //TODO: send PacketHighScore
        }
    }

    public boolean hasEnded() {
        return this.state.equals(GameState.Ended);
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public Map getMap() {
        return this.map;
    }

    /**
     * Sets a new {@link Map} for this {@link GameSession}. Connected clients
     * should be synced here if current computer is the host.
     *
     * @param map {@link Map} to change to.
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Sets a new {@link Map} for this {@link GameSession}. Connected clients
     * should be synced here if the current computer is the host.
     *
     * @param squareSize X and Y dimension of the {@link Map}.
     */
    public void setMap(int squareSize) {
        setMap(new Map(this, squareSize));
    }

    public boolean isPlayerMenuShowing() {
        return this.showingPlayerMenu;
    }

    public void setPlayerMenuShowing(boolean value) {
        this.showingPlayerMenu = value;
    }

    public boolean isPauseMenuShowing() {
        return this.showingPauseMenu;
    }

    public void setPauseMenuShowing(boolean value) {
        this.showingPauseMenu = value;
    }

    public void showPauseMenu() {
        this.showingPauseMenu = !this.showingPauseMenu;
    }

    public void showPlayerMenu() {
        this.showingPlayerMenu = !this.showingPlayerMenu;
    }

    /**
     * Gets the current game {@link Score} this {@link GameSession} is applied
     * to.
     *
     * @return The current {@link Score} from this {@link GameSession}.
     */
    public Score getScore() {
        return this.score;
    }

    public void setScore(Score score) {
        if (score != null) {
            this.score = new Score(score.valueOf());
        }
    }

    /**
     * Returns a {@link Pactale} if found with the specific {@code playerIndex}.
     * If not found, null will be returned.
     *
     * @param playerIndex
     */
    public Pactale getPlayer(int playerIndex) {
        if (this.map == null) {
            return null;
        }
        return this.map.getAllGameObjects()
                .stream()
                .filter(gameObject -> gameObject instanceof Pactale)
                .map(gameObject -> (Pactale) gameObject)
                .filter((Pactale pactale) -> pactale.getPlayerIndex() == playerIndex)
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns a {@link Pactale} if found with the specific {@code id} or null.
     *
     * @param id {@link UUID} to find the {@link Pactale} on.
     * @return {@link Pactale} matching the given {@code id}.
     */
    public Pactale getPlayer(UUID id) {
        if (this.map == null) {
            return null;
        }
        return this.map.getAllGameObjects()
                .stream()
                .filter(obj -> obj instanceof Pactale)
                .map(obj -> (Pactale) obj)
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns the {@link GameObject} found on this {@link Map} for the given
     * {@code id} or null.
     *
     * @param id {@link UUID} to find the {@link GameObject} on.
     * @return {@link GameObject} matching the given {@code id}.
     */
    public GameObject findObject(UUID id) {
        return this.map.getAllGameObjects().stream().filter(obj -> obj.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Return the {@link Item} found on this {@link Map} by the given
     * {@code id}. Will return null if none found.
     *
     * @param id {@link UUID} to search for an {@link Item}.
     * @return {@link Item} matching the given {@code id}.
     */
    public Item findItem(UUID id) {
        return this.map.getAllItems().stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Sets a new {@link Map} for this {@link GameSession}. Connected clients
     * should be synced here if the current computer is the host.
     *
     * @param width X dimension of the {@link Map}.
     * @param height Y dimension of the {@link Map}.
     */
    public void setMap(int width, int height) {
        setMap(new Map(this, width, height));
    }

    /**
     * Return the level of the GameSession object
     *
     * @return int level
     */
    public int getLevel() {
        return level;
    }
}
