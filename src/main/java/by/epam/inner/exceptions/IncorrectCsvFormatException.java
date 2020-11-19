package by.epam.inner.exceptions;

public class IncorrectCsvFormatException extends IllegalArgumentException {
    private final String csvLine;

    public IncorrectCsvFormatException(String csvLine) {
        this.csvLine = csvLine;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ". Incorrect csv line: " + csvLine;
    }
}
