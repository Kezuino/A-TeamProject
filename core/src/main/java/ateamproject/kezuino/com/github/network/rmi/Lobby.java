/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.rmi;

import com.badlogic.gdx.utils.Json;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Fatih
 */
public class Lobby implements Serializable{
    
    private UUID lobbyId;
    private String lobbyName;
    private ArrayList<String> members;
    
    private boolean inGame;
    
     public UUID getLobbyId() {
        return lobbyId;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public ArrayList<String> getMembers() {
        return members;
    }
    
    public boolean addMember(String client)
    {
        return members.add(client);
    }

    
    public boolean isInGame() {
        return inGame;
    }
    
    public Lobby(String lobbyName,String host)
    {
        // generate UUID and give lobby a name
        this.lobbyId = UUID.randomUUID();
        this.lobbyName = lobbyName;
        
        // ingame is set to true if game is started, if started dont show on hostlist
        this.inGame = false;
        
        // add host to members list
        members = new ArrayList<>();
        members.add(host);
    }
    
}
