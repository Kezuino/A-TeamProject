package ateamproject.kezuino.com.github.multiplayer.servers;

import ateamproject.kezuino.com.github.multiplayer.IServer;
import ateamproject.kezuino.com.github.network.rmi.IProtocolServer;
import ateamproject.kezuino.com.github.render.screens.ClanFunctions;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRMI extends UnicastRemoteObject implements IServer, IProtocolServer {
    private transient static ServerRMI instance;

    protected ServerRMI() throws RemoteException {
        super(Registry.REGISTRY_PORT);
    }

    /**
     * Gets or creates a new instance of the {@link ServerRMI}. Doesnt' {@link #start()} the {@link ServerRMI}.
     *
     * @return
     * @throws RemoteException
     */
    public static ServerRMI getInstance() throws RemoteException {
        if (instance == null) {
            instance = new ServerRMI();
        }

        return instance;
    }

    /**
     * Starts up this {@link ServerRMI} listening on connections to this computer on port {@value Registry#REGISTRY_PORT}.
     */
    public void start() {
        System.out.println("Starting server..");

        try {
            System.out.println(Registry.REGISTRY_PORT);
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            registry.bind("server", this);
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        System.out.println("Server started");
    }

    /**
     * Stops this {@link ServerRMI} from listening to clients and drops any active connections. {@link ServerRMI} can be started up again using {@link #start()}.
     */
    public void stop() {
        try {
            System.out.println("Server stopped");
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            System.out.println(ex.getMessage());


            Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean login(String username, String password) {
        System.out.println("Data plxors");

        return true;
    }

    @Override
    public void logout() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> clanFillTable(String emailadres) throws RemoteException {
        return null;
    }

    @Override
    public boolean clanCreateClan(String clanName, String emailaddress) throws RemoteException {
        return false;
    }

    @Override
    public ClanFunctions.InvitationType clanGetInvitation(String clanName, String emailaddress) throws RemoteException {
        return null;
    }

    @Override
    public ClanFunctions.ManagementType getManagement(String clanName, String emailaddress) throws RemoteException {
        return null;
    }

    @Override
    public String getPersons(String clanName) throws RemoteException {
        return null;
    }

    @Override
    public boolean handleInvitation(ClanFunctions.InvitationType invite, String clanName, String emailAddress, String nameOfInvitee) throws RemoteException {
        return false;
    }

    @Override
    public boolean handleManagement(ClanFunctions.ManagementType manage, String clanName, String emailaddress) throws RemoteException {
        return false;
    }

    @Override
    public String getUsername(String emailaddress) throws RemoteException {
        return null;
    }

    @Override
    public String getEmail(String username) throws RemoteException {
        return null;
    }

    @Override
    public boolean setUsername(String name, String emailaddress) throws RemoteException {
        return false;
    }

    @Override
    public void creatureMove(int creatureID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
