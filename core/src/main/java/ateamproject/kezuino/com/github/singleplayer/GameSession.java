package ateamproject.kezuino.com.github.singleplayer;

import java.util.Date;

public class GameSession {

    private final Date startTime;
    private Map map;
    private Score score;
    private GameState state;
    private Boolean showingPlayerMenu;
    private Boolean showingPauseMenu;

    /**
     * Creates a new @see GameSession with the default skin.
     */
    public GameSession() {
        this.showingPlayerMenu = false;
        this.showingPauseMenu = false;
        startTime = new Date();
        this.score = new Score(this);
        this.state = GameState.Running;
    }

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

    public boolean isPlayerMenuShowing() {
        return this.showingPlayerMenu;
    }

    public boolean isPauseMenuShowing() {
        return this.showingPauseMenu;
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

    public void setScore(Score score) {
        if (score != null) {
            this.score = new Score(this, score.valueOf());
        }
    }

    /**
     * Returns a {@link Pactale} if found with the specific {@code playerIndex}.
     * If not found, null will be returned.
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
     * Sets a new {@link Map} for this {@link GameSession}. Connected clients
     * should be synced here if the current computer is the host.
     *
     * @param width X dimension of the {@link Map}.
     * @param height Y dimension of the {@link Map}.
     */
    public void setMap(int width, int height) {
        setMap(new Map(this, width, height));
    }

    public void setPlayerMenuShowing(boolean value) {
        this.showingPlayerMenu = value;
    }

    public void setPauseMenuShowing(boolean value) {
        this.showingPauseMenu = value;
    }
}
