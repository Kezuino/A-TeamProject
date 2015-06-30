package ateamproject.kezuino.com.github.singleplayer;

import java.io.Serializable;

public enum ItemType implements Serializable {
    //BigNugget item/consumable. grants 100 score
    BigObject(150),
    //SmallNugget item/consumable. grants 50 score
    SmallObject(10),

    //test for object
    Watch(0),

    //PowerUps
    Diamond(300),
    Emerald(200),
    Sapphire(150),
    Ruby(100);

    //The score of this {@link ItemType}.
    private final int score;

    /**
     * Constructs and applies score to this {@link ItemType}.
     *
     * @param score The score granted when consumed
     */
    ItemType(int score) {
        this.score = score;
    }

    /**
     * Gets the amount of score to be assigned when this {@link ItemType} is consumed.
     *
     * @return The score which is assigned to the current consumed {@link ItemType}.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Gets the friendly name of the {@link ItemType}.
     *
     * @return Friendly name of the {@link ItemType}.
     */
    @Override
    public String toString() {
        return name().replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}
