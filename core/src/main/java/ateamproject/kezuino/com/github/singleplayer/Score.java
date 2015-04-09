package ateamproject.kezuino.com.github.singleplayer;

import java.util.List;

public class Score {

    private int score;
    private final GameSession gameSession;
    private final long startTime = System.currentTimeMillis();
    private long nextScoreUpdate = 1000;//amount of miliseconds that a score will be decremented
    private int maxScoreManipulation;//the maximal amount of score that will be decremented
    private int currentScoreManipulation = 0;//the current amount of decremented score

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
     * Increases the score with the given value and returns the new current
     * {@link Score} value.
     *
     * @param increaseBy The value to increase the current {@link Score} value
     * with.
     * @return The new current score value of this {@link Score}.
     */
    public int incrementScore(int increaseBy) {
        this.score += increaseBy;
        return this.score;
    }

    /**
     * Decreases the score with the given value and returns the new current
     * {@link Score} value.
     *
     * @param decreaseBy The value to decrease the current {@link Score} value
     * with.
     * @return The new current score value of this {@link Score}.
     */
    public int decrementScore(int decreaseBy) {
        this.score -= this.score - decreaseBy < 0 ? this.score : decreaseBy;
        return this.score;
    }

    /**
     * Will decrement the score every nextScoreUpdate
     * @param gameObjects 
     */
    public void generateNewScore(List<GameObject> gameObjects) {
        if (System.currentTimeMillis() - startTime > nextScoreUpdate && currentScoreManipulation < maxScoreManipulation) {//make sure that the score wont be decremented beyond its initial starting value
            int scoreToDecrement = 0;
            for (GameObject gObject : gameObjects) {
                if (gObject instanceof Pactale) {
                    scoreToDecrement += 60;
                }
            }

            this.decrementScore(scoreToDecrement);
            this.currentScoreManipulation += scoreToDecrement;
            nextScoreUpdate = nextScoreUpdate + 1000;
        }
    }
}
