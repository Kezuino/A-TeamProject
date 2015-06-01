/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.utility.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Kez and Jules
 */
public class Animation {
    private final HashMap<Direction, List<TextureRegion>> textures;
    private List<TextureRegion> upFrames, downFrames, leftFrames, rightFrames;  
    private int currentFrame;
    private boolean hasInitialFrame;
    
    public Animation() {
        this(false);
    }
    
    public Animation(boolean hasInitialFrame) {
        this.upFrames = new ArrayList<>();
        this.downFrames = new ArrayList<>();
        this.leftFrames = new ArrayList<>();
        this.rightFrames = new ArrayList<>();

        this.textures = new HashMap<>();
        this.textures.put(Direction.Up, upFrames);
        this.textures.put(Direction.Down, downFrames);
        this.textures.put(Direction.Left, leftFrames);
        this.textures.put(Direction.Right, rightFrames);
        
        this.currentFrame = 0;
        this.hasInitialFrame = hasInitialFrame;
    }
    
    public void addFrame(Direction direction, Array<? extends TextureRegion> frames) {
        for(TextureRegion t : frames) {
            switch(direction) {
                case Up:
                    this.upFrames.add(t);
                    break;
                case Down:
                    this.downFrames.add(t);
                    break;
                case Left:
                    this.leftFrames.add(t);
                    break;
                case Right:
                    this.rightFrames.add(t);
                    break;
            }            
        }
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
        return (this.downFrames.size() + this.upFrames.size() + this.leftFrames.size() + this.rightFrames.size()) / 4;
    }
}
