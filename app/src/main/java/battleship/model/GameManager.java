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

        this.playerOneGrid = new Grid();
        this.playerTwoGrid = new Grid();
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

    public boolean placeForPlayerOne(Ship ship, int row, int column) {
        try {
            this.playerOneGrid.place(row, column, ship.getNumber(), ship.getOrientation());
        } catch (IncorrectCoordinateException e) {
            e.printStackTrace();
        } catch (IncorrectPlacementException e) {
            return false;
        }
        return true;
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
        this.netManager = new NetworkManager(this);
    }

    public void finishWaiting() {
        this.mainController.closeWaitDialog();
    }

    public void finishJoin() {
        this.mainController.finishJoin();
    }

    public Ship getShipWithNum(int currentShipNum) {
        return this.playerOneGrid.getShipWithNumber(currentShipNum);
    }
}
