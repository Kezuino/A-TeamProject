package ateamproject.kezuino.com.github.singleplayer;

public class Score {

    private int score;
    private GameSession gameSession;

    public int getScore() {
        return this.score;
    }
    
    public GameSession getGameSession() {
        return this.gameSession;
    }

    public int inscrementScore(int score) {
        this.score += score;
        return this.score;
    }

    /**
     * Initializes a score.
     * 
     * @param session The gamesession of this score
     */
    public Score(GameSession session) {
        this.gameSession = session;
    }
}