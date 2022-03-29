package battleship.controller;

import battleship.view.Game;
import battleship.view.GridButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridButtonListener extends MouseAdapter {
    private Game game;
    private MainController mainController;

    public GridButtonListener(Game game, MainController mainController) {
        this.game = game;
        this.mainController = mainController;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GridButton gridButton = (GridButton) e.getSource();
        if (this.mainController.placeShip(this.game.getCurrentShip(), gridButton.getRow(), gridButton.getColumn())) {
            this.game.placeShip(gridButton);
        } else {
            // TODO show that the ship cannot be placed here
            System.out.println("ship cannot be placed here");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        GridButton gridButton = (GridButton) e.getSource();
        if (gridButton.isEnabled() && gridButton.isNotOccupied()) {
            this.game.showPlacement(gridButton, true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        GridButton gridButton = (GridButton) e.getSource();
        if (gridButton.isEnabled() && gridButton.isNotOccupied()) {
            this.game.showPlacement(gridButton, false);
        }
    }
}
