package ateamproject.kezuino.com.github.singleplayer;

public enum ItemType {
    //BigNugget item/consumable. grants 100 score
    BigNugget(100),
    //SmallNugget item/consumable. grants 50 score
    SmallNugget(50),
    
    //PowerUps
    Diamond(250),
    Emerald(200),
    Sapphire(150),
    Ruby(100);
    
    
    //The score of this {@link ItemType}.
    private final int score;
    
    /**
     * Constructs and applies score to this {@link ItemType}.
     * @param score The score granted when consumed
     */
    ItemType(int score) {
        this.score = score;
    }
    
    /**
     * Gets the amount of score to be assigned when this {@link ItemType} is consumed.
     * @return The score which is assigned to the current consumed {@link ItemType}.
     */
    public int getScore() {
        return this.score;
    }
}
