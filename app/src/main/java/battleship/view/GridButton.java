package battleship.view;

import javax.swing.*;

public class GridButton extends JButton {
    private int row;
    private int column;

    public GridButton(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
