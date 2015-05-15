package ateamproject.kezuino.com.github.utility.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawHelper {
    private static Texture pixelTexture;

    /**
     * Creates of gives (if it already exists) the one-pixel {@link Texture}.
     *
     * @return One-pixel {@link Texture}.
     */
    public static Texture getPixelTexture() {
        if (pixelTexture == null) {
            Pixmap pixelPixmap = new Pixmap(1, 1, Pixmap.Format.RGB888);
            pixelPixmap.setColor(Color.WHITE);
            pixelPixmap.fill();
            pixelTexture = new Texture(pixelPixmap);
        }
        return pixelTexture;
    }

    /**
     * Draws a colored rectangle using a one-pixel {@link Texture}.
     *
     * @param batch      {@link SpriteBatch} that will draw the {@link Texture}.
     * @param x          Position on the x-axis to start drawing from.
     * @param y          Position on the y-axis to start drawing from.
     * @param destWidth  Width of the rectangle to draw.
     * @param destHeight Height of the rectangle to draw.
     * @param color      {@link Color} to draw the rectangle in.
     */
    public static void drawRect(SpriteBatch batch, float x, float y, float destWidth, float destHeight, Color color) {
        Color oldColor = batch.getColor();
        batch.setColor(color);
        batch.draw(getPixelTexture(), x, y, destWidth, destHeight);
        batch.setColor(oldColor);
    }
}
