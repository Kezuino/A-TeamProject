package ateamproject.kezuino.com.github.multiplayer;

import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerLauncher {
    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", "darkhellentertainment.com");
        Scanner scanner = new Scanner(System.in);
        Server server;
        
        try {
            server = Server.instance();
            server.start();
            System.out.println("Waiting for input..");

            while(true){
                int line = scanner.nextInt();
                if(line == 0)
                    break;
            }
            server.stop();
        } catch (RemoteException ex) {
            Logger.getLogger(ServerLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    }
}
