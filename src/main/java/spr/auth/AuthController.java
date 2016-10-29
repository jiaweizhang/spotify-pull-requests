package spr.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
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
    public ModelAndView authorizeRedirect(@RequestParam(value = "code", required = false) String code,
                                          @RequestParam(value = "error", required = false) String error,
                                          @RequestParam(value = "state", required = true) String state) {
        String jwtToken = authService.authorize(code, error, state);

        if (jwtToken == null) {
            return new ModelAndView("redirect:" + "/access-denied.html");
        }

        String redirectUrl = "/clientredirect#jwt=" + jwtToken;

        return new ModelAndView("redirect:" + redirectUrl);
    }
}
