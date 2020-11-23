package by.epam.inner.exceptions;

public class SQLNotFoundPropertyException extends IllegalArgumentException {
    public SQLNotFoundPropertyException() {
    }

    public SQLNotFoundPropertyException(String s) {
        super(s);
    }

    public SQLNotFoundPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLNotFoundPropertyException(Throwable cause) {
        super(cause);
    }
}
