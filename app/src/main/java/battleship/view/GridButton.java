package battleship.view;

import javax.swing.*;

public class GridButton extends JButton {
    private int row;
    private int column;
    private boolean isOccupied;

    public GridButton(int row, int column) {
        this.row = row;
        this.column = column;
        this.isOccupied = false;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isNotOccupied() {
        return !isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}
