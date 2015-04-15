package ateamproject.kezuino.com.github.render.debug.renderers;

import ateamproject.kezuino.com.github.render.debug.DebugLayers;
import ateamproject.kezuino.com.github.render.debug.DebugRenderer;
import ateamproject.kezuino.com.github.singleplayer.GameObject;
import ateamproject.kezuino.com.github.singleplayer.Node;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Anton on 4/4/2015.
 */
public class DebugMovement extends DebugRenderer<GameObject> {
    private ShapeRenderer renderer;

    public DebugMovement() {
        super(DebugLayers.GameObject);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
    }

    @Override
    public void render(GameObject gameObject) {
        super.render(gameObject);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin();
        renderer.set(ShapeRenderer.ShapeType.Filled);

        // Draw current node.
        renderer.setColor(Color.RED.tmp().add(0, 0, 0, -0.6f));
        renderer.rect(gameObject.getExactPosition().x, gameObject.getExactPosition().y, 32, 32);

        // Draw target node.
        if (gameObject.getMoveEndNode() != null) {
            Node targetNode = gameObject.getMoveEndNode();

            renderer.setColor(Color.GREEN.tmp().add(0, 0, 0, -0.6f));
            renderer.rect(targetNode.getExactPosition().x, targetNode.getExactPosition().y, 32, 32);
        }

        renderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
