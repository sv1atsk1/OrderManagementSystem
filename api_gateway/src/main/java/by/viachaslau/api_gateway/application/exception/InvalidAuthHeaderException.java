package by.viachaslau.api_gateway.application.exception;


public class InvalidAuthHeaderException extends RuntimeException {
    public InvalidAuthHeaderException(String message) {
        super(message);
    }
}
