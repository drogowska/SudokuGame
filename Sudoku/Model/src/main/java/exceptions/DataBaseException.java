package exceptions;


public class DataBaseException extends DaoException {
    public DataBaseException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DataBaseException(String message) {
        super(message);
    }
}
