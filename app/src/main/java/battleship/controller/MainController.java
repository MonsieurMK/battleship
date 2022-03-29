package battleship.controller;

import battleship.model.GameManager;
import battleship.model.Ship;
import battleship.view.MainMenu;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainController {

    private final MainMenu mainMenu;
    private final GameManager gameManager;

    private InetAddress localAddress;

    public MainController() {
        this.mainMenu = new MainMenu(this);
        this.gameManager = new GameManager(this);

        try {
            this.localAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getLocalAddress() {
        return localAddress;
    }

    public void hostGame() {
        this.gameManager.hostGame();
        this.mainMenu.displayLoading();
    }

    public void interruptHosting() {
        this.gameManager.interruptHosting();
    }

    public void joinGame() {
        InetAddress hostAddress = this.mainMenu.requestJoiningAddress();
        if (hostAddress != null) {
            this.gameManager.joinGame();
        }
    }

    public void closeWaitDialog() {
        this.mainMenu.closeLoading();
        this.mainMenu.setButtonsToStart();
    }

    public void finishJoin() {
        this.mainMenu.setButtonsToStart();
    }

    public Ship getShipWithNumber(int currentShipNum) {
        return this.gameManager.getShipWithNum(currentShipNum);
    }

    public boolean placeShip(Ship currentShip, int row, int column) {
        return this.gameManager.placeForPlayerOne(currentShip, row, column);
    }
}
