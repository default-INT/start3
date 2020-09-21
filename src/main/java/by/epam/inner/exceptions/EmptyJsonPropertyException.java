package by.epam.inner.exceptions;

public class EmptyJsonPropertyException extends IllegalArgumentException {
    private final String propertyName;

    public EmptyJsonPropertyException(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ". Property '" + propertyName + "' is empty,";
    }
}
