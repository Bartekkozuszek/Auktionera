package se.iths.auktionera.api.exception;

public class InvalidCategoryException extends RuntimeException{
    public InvalidCategoryException(String message) {
        super(message);
    }
}
