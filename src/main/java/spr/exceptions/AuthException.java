package spr.exceptions;

/**
 * Created by jiaweizhang on 10/29/2016.
 */
public class AuthException extends RuntimeException {
    public String message;

    public AuthException(String message) {
        this.message = message;
    }
}
