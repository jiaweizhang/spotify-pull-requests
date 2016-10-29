package spr.auth;

import spr.exceptions.AuthException;
import spr.std.Service;
import spr.std.models.StdResponse;
import utilities.AuthUtility;
import utilities.models.TokenResponse;

/**
 * Created by jiaweizhang on 10/29/2016.
 */

@org.springframework.stereotype.Service
public class AuthService extends Service {

    public StdResponse authorize(String code, String state) {
        // validate hardcoded state variable
        if (!state.equals("spr")) {
            throw new AuthException("state does not equal spr");
        }

        // get access_token and refresh_token
        TokenResponse tokenResponse = AuthUtility.tokenCode(code);
        System.out.println(tokenResponse.toString());

        // retrieve user information and store into database
        // TODO

        return new StdResponse(200, true, tokenResponse.toString());
    }
}
