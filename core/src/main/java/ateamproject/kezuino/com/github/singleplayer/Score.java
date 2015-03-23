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

    public void setScore(int score) {
        this.score = score;
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