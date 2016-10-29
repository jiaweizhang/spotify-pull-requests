package spr.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spr.exceptions.AuthException;
import spr.std.Controller;

/**
 * Created by jiaweizhang on 10/29/2016.
 */

@RestController
public class AuthController extends Controller {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/redirect",
    method = RequestMethod.GET)
    public ResponseEntity authorizeRedirect(@RequestParam(value = "code", required = true) String code,
                                            @RequestParam(value = "state", required = true) String state) {
        return wrap(authService.authorize(code, state));
    }
}
