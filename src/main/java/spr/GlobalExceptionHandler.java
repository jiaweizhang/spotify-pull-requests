package spr; /**
 * Created by jiaweizhang on 10/29/2016.
 */


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import spr.std.Controller;
import spr.std.models.StdResponse;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends Controller {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(Exception e) {
        return wrap(new StdResponse(200, false, "Invalid request: " + e.getLocalizedMessage()));
    }

}