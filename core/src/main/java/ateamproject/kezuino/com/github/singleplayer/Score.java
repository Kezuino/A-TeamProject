package ateamproject.kezuino.com.github.singleplayer;

public class Score {

    private int score;
    private GameSession gameSession;

    /**
     * Initializes a score.
     *
     * @param session The gamesession of this score
     */
    public Score(GameSession session) {
        this.gameSession = session;
    }

    public int getScore() {
        return this.score;
    }

    public GameSession getGameSession() {
        return this.gameSession;
    }

    public int incrementScore(int score) {
        this.score += score;
        return this.score;
    }
}