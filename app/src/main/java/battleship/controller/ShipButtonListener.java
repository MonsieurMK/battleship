package battleship.controller;

import battleship.view.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShipButtonListener implements ActionListener {

    private final Game game;

    public ShipButtonListener(Game game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        this.game.setCurrentShipNum(btn);
        this.game.setPlayerButtons(true, true); // enable player buttons
    }
}
