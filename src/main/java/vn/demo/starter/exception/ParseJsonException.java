package vn.demo.starter.exception;

public class ParseJsonException extends RuntimeException {
    public ParseJsonException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
