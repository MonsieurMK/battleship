package battleship.model;

import java.util.Objects;

public class Coordinate {
    private final int row;
    private final int column;

    public Coordinate(int row, int column)
            throws IncorrectCoordinateException {
        if (row < 0 || row >= Grid.GRID_SIZE || column < 0 || column >= Grid.GRID_SIZE) {
            throw new IncorrectCoordinateException("coordinates out of the grid");
        }
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
