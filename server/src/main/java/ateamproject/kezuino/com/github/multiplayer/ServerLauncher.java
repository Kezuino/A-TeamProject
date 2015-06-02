package ateamproject.kezuino.com.github.multiplayer;

import ateamproject.kezuino.com.github.network.rmi.Server;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerLauncher {
    private static boolean isPrinting;

    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", "localhost");
        
        Scanner scanner = new Scanner(System.in);
        Server server;
        isPrinting = true;

        try {
            server = Server.getInstance();
            server.start();

            while(isPrinting){
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if(line.equals("exit")) {
                        isPrinting = false;
                        break;
                    }
                }
                
                // Do not over-use the CPU's resources.
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            server.stop();
        } catch (RemoteException ex) {
            Logger.getLogger(ServerLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
