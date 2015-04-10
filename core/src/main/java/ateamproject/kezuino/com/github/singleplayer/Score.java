package ateamproject.kezuino.com.github.singleplayer;

import java.util.List;
import java.util.stream.Collectors;

public class Score {

    private final GameSession gameSession;
    private final long startTime = System.currentTimeMillis();
    /**
     * The maximal amount of score that will be decremented.
     */
    private final int maxScoreManipulation;
    private int score;
    /**
     * Amount of miliseconds that a score will be decremented.
     */
    private long nextScoreUpdate = 1000;
    /**
     * The current amount of decremented score.
     */
    private int currentScoreManipulation = 0;

    /**
     * Constructs a new {@link Score} for the set {@link GameSession}.
     *
     * @param session The {@link GameSession} of this score.
     */
    public Score(GameSession session) {
        this.score = 5000;
        this.gameSession = session;
        this.maxScoreManipulation = this.score;
    }

    /**
     * Get the current {@link Score} value of the set {@link GameSession}.
     *
     * @return The current {@link Score} of this {@link GameSession}.
     */
    public int valueOf() {
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
     * Increases the score with the given value and returns the new current
     * {@link Score} value.
     *
     * @param increaseBy The value to increase the current {@link Score} value
     *                   with.
     * @return The new current score value of this {@link Score}.
     */
    public int increase(int increaseBy) {
        if (increaseBy < 0) throw new IllegalArgumentException("Parameter increaseBy must not be negative.");
        this.score += increaseBy;
        return this.score;
    }

    /**
     * Decreases the score with the given value and returns the new current
     * {@link Score} value. If the next step would cause the score to go beyond 0
     * it will decrease the score by itself (turning it into 0).
     *
     * @param decreaseBy The value to decrease the current {@link Score} value
     *                   with.
     * @return The new current score value of this {@link Score}.
     */
    public int decrease(int decreaseBy) {
        if (decreaseBy < 0) throw new IllegalArgumentException("Parameter decreaseBy must not be negative.");
        this.score -= this.score - decreaseBy < 0 ? this.score : decreaseBy;
        return this.score;
    }

    /**
     * Will decrease the score every nextScoreUpdate
     *
     * @param gameObjects
     */
    public void generateNewScore(List<GameObject> gameObjects) {
        if (System.currentTimeMillis() - startTime > nextScoreUpdate && currentScoreManipulation < maxScoreManipulation) {//make sure that the score wont be decremented beyond its initial starting value
            int scoreToDecrement = gameObjects.stream()
                                              .filter(go -> go instanceof Pactale)
                                              .collect(Collectors.counting())
                                              .intValue() * 60;

            this.decrease(scoreToDecrement);
            this.currentScoreManipulation += scoreToDecrement;
            nextScoreUpdate = nextScoreUpdate + 1000;
        }
    }
}
