package battleship.model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Ship {
    private final int size;
    private final String name;
    private final LinkedList<Coordinate> coordinates;
    private Orientation orientation;
    private final State[] states;
    private boolean isPlaced;

    public Ship(int size, String name) {
        this.size = size;
        this.name = name;
        this.states = new State[this.size];
        for (int i = 0; i < this.size; i++) {
            this.states[i] = State.OCCUPIED;
        }
        this.orientation = Orientation.HORIZONTAL;
        this.coordinates = new LinkedList<>();
        this.isPlaced = false;
    }

    public void place(int baseRow, int baseColumn) {
        try {
            this.coordinates.add(new Coordinate(baseRow, baseColumn));

            if (this.orientation == Orientation.HORIZONTAL) {
                for (int i = 1; i < this.size; i++) {
                    this.coordinates.add(new Coordinate(baseRow, baseColumn + i));
                }
            } else {
                for (int i = 1; i < this.size; i++) {
                    this.coordinates.add(new Coordinate(baseRow + i, baseColumn));
                }
            }
        } catch (IncorrectCoordinateException e) {
            e.printStackTrace();
        }
        this.isPlaced = true;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public boolean isSunk() {
        for (State state :
                this.states) {
            if (state == State.OCCUPIED) {
                return false;
            }
        }
        return true;
    }

    public void rotate() {
        if (this.orientation == Orientation.HORIZONTAL) {
            this.orientation = Orientation.VERTICAL;
        } else {
            this.orientation = Orientation.HORIZONTAL;
        }
    }

    public int getSize() {
        return size;
    }

    public LinkedList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public boolean occupiesCoordinate(int row, int column) {
        Coordinate coordinateToCompare = null;
        try {
            coordinateToCompare = new Coordinate(row, column);
        } catch (IncorrectCoordinateException e) {
            e.printStackTrace();
        }
        for (Coordinate coordinate :
                this.coordinates) {
            if (coordinate.equals(coordinateToCompare)) {
                return true;
            }
        }
        return false;
    }

    public void receiveAttack(int row, int column) {
        for (int i = 0; i < this.size; i++) {
            try {
                if (this.coordinates.get(i).equals(new Coordinate(row, column))) {
                    this.states[i] = State.HIT;
                }
            } catch (IncorrectCoordinateException e) {
                e.printStackTrace();
            }
        }
        if (this.isSunk()) {
            for (int i = 0; i < this.size; i++) {
                this.states[i] = State.SUNK;
            }
        }
    }

    public String showStates() {
        StringBuilder s = new StringBuilder();
        for (State state :
                this.states) {
            s.append(state.name()).append(",");
        }
        return s.toString();
    }
}
