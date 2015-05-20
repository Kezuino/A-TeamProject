package ateamproject.kezuino.com.github.utility.game.balloons.messages;

import ateamproject.kezuino.com.github.utility.game.IPositionable;
import ateamproject.kezuino.com.github.utility.game.balloons.BalloonMessage;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BalloonHelpMe extends BalloonMessage {
    public BalloonHelpMe() {
        this.name = "help";
        this.loadBalloon();
    }
}
