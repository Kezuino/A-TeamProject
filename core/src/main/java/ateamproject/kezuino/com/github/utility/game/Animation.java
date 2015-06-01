/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.utility.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.HashMap;

/**
 *
 * @author Kez and Jules
 */
public class Animation {
    private final HashMap<Direction, Array<TextureRegion>> textures;
    private int currentFrame;
    private final boolean hasInitialFrame;
    
    public Animation(Texture frames) {
        this(false, frames);
    }
    
    public Animation(boolean hasInitialFrame, Texture frames) {
        TextureRegion[][] region = TextureRegion.split(frames, 32, 32);

        this.textures = new HashMap<>();
        this.textures.put(Direction.Down, new Array(region[0]));
        this.textures.put(Direction.Right, new Array(region[1]));
        this.textures.put(Direction.Up, new Array(region[2]));
        this.textures.put(Direction.Left, new Array(region[3]));
        this.currentFrame = 0;
        this.hasInitialFrame = hasInitialFrame;
    }

    public TextureRegion getFrame(Direction direction) {
        return this.textures.get(direction).get(this.currentFrame);
    }
    
    public void nextFrame() {
        this.currentFrame = this.currentFrame + 1 >= this.frameSize() ? this.hasInitialFrame ? 1 : 0 : this.currentFrame + 1;
    }
    
    public void resetFrame() {
        if(this.hasInitialFrame) {
            this.currentFrame = 0;
        }
    }
    
    public int size() {
        return this.textures.size();
    }
    
    public int frameSize() {
        return (this.textures.get(Direction.Down).size + this.textures.get(Direction.Up).size + this.textures.get(Direction.Left).size + this.textures.get(Direction.Right).size) / 4;
    }
}
