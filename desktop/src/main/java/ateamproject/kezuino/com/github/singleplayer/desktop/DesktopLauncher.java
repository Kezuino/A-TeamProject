package ateamproject.kezuino.com.github.singleplayer.desktop;

import ateamproject.kezuino.com.github.PactaleGame;
import ateamproject.kezuino.com.github.network.rmi.Client;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class DesktopLauncher {

    public static void main(String[] arg) {
        System.setProperty("java.rmi.server.hostname", getLocalIP());

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.foregroundFPS = 60;
        cfg.backgroundFPS = 30;
        cfg.useGL30 = false;

        //cfg.samples = 8;
            
        //frameRateTest(cfg);
        PactaleGame game = new PactaleGame();
        new LwjglApplication(game, cfg);

        Client client = Client.getInstance();
        client.setGame(game);
        client.start();

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    public static void frameRateTest(LwjglApplicationConfiguration cfg) {
        cfg.foregroundFPS = 0;
        cfg.backgroundFPS = 0;
        cfg.width = 1280;
        cfg.height = 720;
        cfg.fullscreen = false;
        cfg.forceExit = true;
        cfg.vSyncEnabled = false;
    }
    
    public static String getLocalIP() {
        String ipAddress = null;
        Enumeration<NetworkInterface> net;
        try {
            net = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        while(net.hasMoreElements()){
            NetworkInterface element = net.nextElement();
            Enumeration<InetAddress> addresses = element.getInetAddresses();

            while (addresses.hasMoreElements()){
                InetAddress ip = addresses.nextElement();
                if (ip instanceof Inet4Address){
                    if (ip.isSiteLocalAddress()){
                        ipAddress = ip.getHostAddress();
                    }
                }
            }
        }
        return ipAddress == null ? "localhost" : ipAddress;
    }
}
