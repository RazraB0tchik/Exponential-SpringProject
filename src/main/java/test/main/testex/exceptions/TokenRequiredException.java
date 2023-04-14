package test.main.testex.exceptions;

public class TokenRequiredException extends RuntimeException{
    public TokenRequiredException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TokenRequiredException(String msg) {
        super(msg);
    }
}
