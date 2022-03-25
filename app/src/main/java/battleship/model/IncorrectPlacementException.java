package battleship.model;

public class IncorrectPlacementException extends Exception {
    public IncorrectPlacementException(String message) {
        super(message);
    }
}
