/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.singleplayer;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Kez and Jules
 */
public class Animation {
    private final HashMap<Direction, List<Texture>> textures;
    private final List<Texture> upFrames, downFrames, leftFrames, rightFrames;  
    private int currentFrame;
    
    public Animation() {
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
    }
    
    public void addFrame(Direction direction, Texture texture) {
        switch(direction) {
            case Up:
                this.upFrames.add(texture);
                break;
            case Down:
                this.downFrames.add(texture);
                break;
            case Left:
                this.leftFrames.add(texture);
                break;
            case Right:
                this.rightFrames.add(texture);
                break;
        }
    }
    
    public Texture getFrame(Direction direction) {
        return this.textures.get(direction).get(this.currentFrame);
    }
    
    public void nextFrame() {
        this.currentFrame = this.currentFrame + 1 >= this.frameSize() ? 0 : this.currentFrame + 1;
    }
    
    public int size() {
        return this.textures.size();
    }
    
    public int frameSize() {
        return (this.downFrames.size() + this.upFrames.size() + this.leftFrames.size() + this.rightFrames.size()) / 4;
    }
}
