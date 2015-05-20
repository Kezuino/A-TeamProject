/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.render.screens;

import java.util.ArrayList;

/**
 *
 * @author Fatih
 */
public class LobbyFunctions {
 
    
    public ArrayList<String[]> getRandomHostList()
    {
        // 0 = lobby name
        // 1 = host name
        // 2 = members
        ArrayList<String[]> HostsList = new ArrayList<>();
        
        String[] host;
        for (int i = 0; i < 20; i++) {
             host = new String[3];
             host[0] = "NewGame";
             host[1] = "Fatih";
             host[2] = "1/8";
             HostsList.add(host);
        }
        
        return HostsList;
    }
    
    public ArrayList<String[]> getClanHostList()
    {
        // 0 = lobby name
        // 1 = host name
        // 2 = members
        ArrayList<String[]> ClansList = new ArrayList<>();
        
        String[] host;
        for (int i = 0; i < 20; i++) {
             host = new String[3];
             host[0] = "NewGame";
             host[1] = "Fatih";
             host[2] = "1/8";
             ClansList.add(host);
        }
        
        return ClansList;
    }
    
}
