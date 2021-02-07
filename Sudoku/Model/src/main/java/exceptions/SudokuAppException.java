package exceptions;

public class SudokuAppException extends RuntimeException {
    public SudokuAppException(String message) {
        super(message);
    }

    public SudokuAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
