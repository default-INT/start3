package by.epam.inner.exceptions;

public class IncorrectAccountFormatException extends IllegalArgumentException {
    private String login;

    public IncorrectAccountFormatException() {
    }

    public IncorrectAccountFormatException(String s) {
        super(s);
    }

    public IncorrectAccountFormatException(String s, String login) {
        super(s);
        this.login = login;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        if (login != null && !login.isEmpty()) {
            stringBuilder.append(". login = ").append(login);
        }
        return super.getMessage() + stringBuilder.toString();
    }
}
