package battleship.model;

import java.util.HashMap;

public class Grid {
    public static final int GRID_SIZE = 10;
    public static final int SHIP_COUNT = 5;
    public static final String[] SHIP_NAMES = {"Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer"};
    public static final int[] SHIP_SIZES = {5, 4, 3, 3, 2};

    //private ArrayList<Coordinate> coordinates;
    private final HashMap<Coordinate, State> coordinates;
    private final Ship[] ships;

    public Grid() {
        this.coordinates = new HashMap<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                try {
                    this.coordinates.put(new Coordinate(i, j), State.NONE);
                } catch (IncorrectCoordinateException e) {
                    e.printStackTrace();
                }
            }
        }
        this.ships = new Ship[SHIP_COUNT];
        for (int i = 0; i < SHIP_COUNT; i++) {
            this.ships[i] = new Ship(SHIP_SIZES[i], SHIP_NAMES[i]);
        }
    }

    public void place(int row, int column, int shipNum, Orientation orientation)
            throws IncorrectCoordinateException, IncorrectPlacementException {
        if (row < 0 || row >= Grid.GRID_SIZE || column < 0 || column >= Grid.GRID_SIZE) {
            throw new IncorrectCoordinateException("coordinates out of the grid");
        }

        Ship ship = this.ships[shipNum];
        ship.setOrientation(orientation);

        if (ship.isPlaced()) {
            throw new IncorrectPlacementException("ship already placed");
        }

        // checking if can be placed
        for (int i = 0; i < ship.getSize(); i++) {
            if (ship.getOrientation() == Orientation.HORIZONTAL) {
                if ((column + i) >= GRID_SIZE) {
                    throw new IncorrectPlacementException("out of grid bounds");
                }
                if (this.coordinates.get(new Coordinate(row, column + i)) != State.NONE) {
                    throw new IncorrectPlacementException("overlaps on another ship");
                }
            } else {
                if ((row + i) >= GRID_SIZE) {
                    throw new IncorrectPlacementException("out of grid bounds");
                }
                if (this.coordinates.get(new Coordinate(row + i, column)) != State.NONE) {
                    throw new IncorrectPlacementException("overlaps on another ship");
                }
            }
        }
        
        ship.place(row, column);
        for (Coordinate coordinate :
                ship.getCoordinates()) {
            this.coordinates.remove(coordinate);
            this.coordinates.put(coordinate, State.OCCUPIED);
        }
    }

    public void receiveAttack(int row, int column)
            throws IncorrectCoordinateException, UnothaurizedAttackException {
        if (row < 0 || row >= Grid.GRID_SIZE || column < 0 || column >= Grid.GRID_SIZE) {
            throw new IncorrectCoordinateException("coordinates out of the grid");
        }

        Coordinate coordinate = new Coordinate(row, column);

        switch (this.coordinates.get(coordinate)) {
            case NONE -> {
                this.coordinates.remove(coordinate);
                this.coordinates.put(coordinate, State.MISSED);
            }
            case OCCUPIED -> {
                this.coordinates.remove(coordinate);
                Ship ship = this.ships[this.getShipNumByCoordinate(row, column)];
                ship.receiveAttack(row, column);
                if (ship.isSunk()) {
                    this.coordinates.put(coordinate, State.SUNK);
                    for (Coordinate coordinate1 :
                            ship.getCoordinates()) {
                        this.coordinates.remove(coordinate1);
                        this.coordinates.put(coordinate1, State.SUNK);
                    }
                } else {
                    this.coordinates.put(coordinate, State.HIT);
                }
            }
            default -> throw new UnothaurizedAttackException();
        }
    }

    private int getShipNumByCoordinate(int row, int column) {
        for (int i = 0; i < SHIP_COUNT; i++) {
            if (this.ships[i].occupiesCoordinate(row, column)) {
                return i;
            }
        }
        return -1; // no ship occupies
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        try {
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    switch (this.coordinates.get(new Coordinate(i, j))) {
                        case NONE -> s.append("|   |");
                        case OCCUPIED -> s.append("| O |");
                        case HIT -> s.append("| X |");
                        case SUNK -> s.append("| _ |");
                        case MISSED -> s.append("| - |");
                        default -> throw new IllegalStateException("Unexpected value: ");
                    }
                }
                s.append("\n");
            }
        } catch (IncorrectCoordinateException e) {
            e.printStackTrace();
        }

        return s.toString();
    }
}
