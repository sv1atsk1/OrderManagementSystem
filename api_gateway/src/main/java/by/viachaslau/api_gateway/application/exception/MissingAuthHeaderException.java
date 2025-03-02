package by.viachaslau.api_gateway.application.exception;

public class MissingAuthHeaderException extends RuntimeException {
    public MissingAuthHeaderException(String message) {
        super(message);
    }
}