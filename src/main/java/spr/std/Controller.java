package spr.std;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spr.exceptions.AuthException;
import spr.std.models.StdRequest;
import spr.std.models.StdResponse;
import utilities.JwtUtility;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by jiaweizhang on 10/29/2016.
 */

public class Controller {

    public StdRequest pre(HttpServletRequest httpServletRequest) {
        StdRequest stdRequest = new StdRequest();
        pre(stdRequest, httpServletRequest);
        return stdRequest;
    }

    public void pre(StdRequest stdRequest, HttpServletRequest httpServletRequest) {
        String jwt = httpServletRequest.getHeader("Authorization");
        try {
            stdRequest.spotifyId = JwtUtility.retrieveSpotifyId(jwt);
        } catch (Exception e) {
            throw new AuthException("Jwt invalid");
        }
    }

    protected ResponseEntity wrap(StdResponse stdResponse) {
        stdResponse.timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        switch (stdResponse.status) {
            case 200:
                return ResponseEntity.status(HttpStatus.OK).body(stdResponse);
            case 403:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(stdResponse);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(stdResponse);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(stdResponse);
        }
    }
}
