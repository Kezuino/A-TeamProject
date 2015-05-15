package ateamproject.kezuino.com.github.utility.game.balloons.messages;

import ateamproject.kezuino.com.github.utility.game.balloons.BalloonMessage;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class BalloonHelpMe extends BalloonMessage {

    @Override
    public Pixmap createGraphic() {
        Pixmap pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0, 0, 0, 0.5f));
        pixmap.fill();
        return pixmap;
    }
}
