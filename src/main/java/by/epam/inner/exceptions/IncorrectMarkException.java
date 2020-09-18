package by.epam.inner.exceptions;

public class IncorrectMarkException extends IllegalArgumentException {
    public IncorrectMarkException() {
        super();
    }

    public IncorrectMarkException(String s) {
        super(s);
    }

    public IncorrectMarkException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectMarkException(Throwable cause) {
        super(cause);
    }
}
