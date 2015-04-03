package ateamproject.kezuino.com.github.singleplayer;

public enum ItemType {
    BigNugget(100),
    SmallNugget(50);
    
    private final int score;
    
    ItemType(int score) {
        this.score = score;
    }
    
    public int getScore() {
        return this.score;
    }
}
