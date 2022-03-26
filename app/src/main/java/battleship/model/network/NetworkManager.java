package battleship.model.network;

import battleship.model.GameManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class NetworkManager extends Thread {

    public static final int PORT = 9000;

    private final GameManager gameManager;

    private Socket sock;
    private ServerSocket serverSocket;

    public NetworkManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        try {
            System.out.println("Waiting...");
            this.serverSocket = new ServerSocket(PORT);
            this.sock = serverSocket.accept();
            System.out.println("Player joined");
            this.gameManager.finishWaiting();
        } catch (IOException e) {
            if (!(e instanceof SocketException))
                e.printStackTrace();
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void host() {
        this.start();

    }

    public void joinGame(InetAddress address) {
        try {
            this.sock = new Socket(address, PORT);
            System.out.println("joined");
            this.gameManager.finishJoin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        // TODO
    }

    public void receiveStartGame() {
        // TODO
    }

    public boolean sendPlacement() {
        // TODO
        return false;
    }

    public boolean attack() {
        // TODO
        return false;
    }

    public boolean receivePlacement() {
        // TODO
        return false;
    }

}
