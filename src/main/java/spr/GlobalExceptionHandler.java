package spr;

/**
 * Created by jiaweizhang on 10/29/2016.
 */


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import spr.exceptions.AuthException;
import spr.exceptions.PropertyLoaderException;
import spr.exceptions.WrapperException;
import spr.std.Controller;
import spr.std.models.StdResponse;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends Controller {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(Exception e) {
        return wrap(new StdResponse(200, false, "Invalid request: " + e.getLocalizedMessage()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity handleAuthException(AuthException e) {
        return wrap(new StdResponse(403, false, "Auth error: " + e.message));
    }

    @ExceptionHandler(PropertyLoaderException.class)
    public ResponseEntity handlePropertyLoaderException(Exception e) {
        return wrap(new StdResponse(500, false, "Error with property loader"));
    }

    @ExceptionHandler(WrapperException.class)
    public ResponseEntity handleWrapperException(Exception e) {
        return wrap(new StdResponse(500, false, "Error with Java Spotify Web API wrapper"));
    }
}