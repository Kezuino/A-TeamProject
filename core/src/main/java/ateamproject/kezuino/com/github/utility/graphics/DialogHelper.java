package ateamproject.kezuino.com.github.utility.graphics;

import ateamproject.kezuino.com.github.render.screens.BaseScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class DialogHelper {
    private static Skin skin;
    private static Game game;

    public static void setSkin(Skin skin) {
        DialogHelper.skin = skin;
    }

    public static void setGame(Game game) {
        DialogHelper.game = game;
    }

    public static Stage getStage() {
        if (DialogHelper.game == null) return null;
        BaseScreen screen = ((BaseScreen) DialogHelper.game.getScreen());
        return screen.getStage();
    }

    /**
     * Shows a simple {@link Dialog} on the current screen with an OK button.
     *
     * @param title   Title to show in the topbar of the {@link Dialog}.
     * @param message Message to show to the user.
     * @return
     */
    public static Dialog show(String title, String message) {
        Dialog dialog = new Dialog(title, skin);
        dialog.add(message);
        TextButton bExit = new TextButton("Oke", skin);
        bExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.remove();
            }
        });
        dialog.add(bExit);
        dialog.show(getStage());
        return dialog;
    }

    /**
     * Shows a simple {@link Dialog} on the current screen with an OK button.
     *
     * @param title    Title to show in the topbar of the {@link Dialog}.
     * @param message  Message to show to the user.
     * @param listener Listener to execute when the user interacts with the {@link Dialog}.
     * @return
     */
    public static Dialog show(String title, String message, DialogClickListener listener) {
        Dialog dialog = new Dialog(title, skin);
        dialog.add(message);
        TextButton bExit = new TextButton("Oke", skin);
        bExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                listener.click(dialog, x, y);
            }
        });
        dialog.add(bExit);
        dialog.show(getStage());
        return dialog;
    }


    @FunctionalInterface
    public interface DialogClickListener {
        void click(Dialog d, float x, float y);
    }
}
