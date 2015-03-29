package ateamproject.kezuino.com.github.singleplayer;

public class Score {

    private int score;
    private final GameSession gameSession;

    /**
     * Constructs a new {@link Score} for the set {@link GameSession}.
     *
     * @param session The {@link GameSession} of this score.
     */
    public Score(GameSession session) {
        this.score = 0;
        this.gameSession = session;
    }

    /**
     * Get the current {@link Score} value of the set {@link GameSession}.
     * 
     * @return The current {@link Score} of this {@link GameSession}.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Gets the current set {@link GameSession} to this {@link Score}.
     * 
     * @return The set {@link GameSession} to this {@link Score}.
     */
    public GameSession getGameSession() {
        return this.gameSession;
    }

    /**
     * Increases the score with the given value and returns the new current {@link Score} value.
     * 
     * @param increaseBy The value to increase the current {@link Score} value with.
     * @return The new current score value of this {@link Score}.
     */
    public int incrementScore(int increaseBy) {
        this.score += increaseBy;
        return this.score;
    }
    
    /**
     * Decreases the score with the given value and returns the new current {@link Score} value.
     * 
     * @param decreaseBy The value to decrease the current {@link Score} value with.
     * @return The new current score value of this {@link Score}.
     */
    public int decrementScore(int decreaseBy) {
        this.score -= decreaseBy;
        return this.score;
    }
}