package test.main.testex.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AccessTokenException extends Exception {

    public AccessTokenException(String msg) {
        super(msg);
    }

    public AccessTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
