package battleship.model;

public class UnothaurizedAttackException extends Exception {
    public UnothaurizedAttackException(String message) {
        super(message);
    }

    public UnothaurizedAttackException() {
    }
}
