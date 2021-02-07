package exceptions;

public class DaoException extends SudokuAppException {

    public DaoException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DaoException(String message) {
        super(message);
    }
}
