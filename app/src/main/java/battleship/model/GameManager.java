package battleship.model;

import battleship.controller.MainController;
import battleship.model.network.NetworkManager;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GameManager {
    private Grid playerOneGrid;
    private Grid playerTwoGrid;

    private NetworkManager netManager;

    private final MainController mainController;

    public GameManager(MainController mainController) {
        this.netManager = new NetworkManager(this);
        this.mainController = mainController;
    }

    public void hostGame() {
        this.netManager.host();
    }

    public void joinGame() {
        try {
            this.netManager.joinGame(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        // TODO
    }

    public void selectShip() {
        // TODO
    }

    public boolean place() {
        // TODO
        return false;
    }

    public void finishPlacement() {
        // TODO
    }

    public State attack() {
        // TODO
        return null;
    }

    public void interruptHosting() {
        this.netManager.interrupt();
        System.out.println("Interrupted");
        this.netManager = new NetworkManager(this);
    }

    public void finishWaiting() {
        this.mainController.closeWaitDialog();
    }

    public void finishJoin() {
        this.mainController.finishJoin();
    }
}
