package battleship.controller;

import battleship.view.Game;
import battleship.view.GridButton;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaceHoverListener extends MouseAdapter {
    private Game game;

    public PlaceHoverListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.game.showPlacement((GridButton) e.getSource(), true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.game.showPlacement((GridButton) e.getSource(), false);
    }
}
