package spr.auth;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.CurrentUserRequest;
import com.wrapper.spotify.models.User;
import spr.exceptions.AuthException;
import spr.std.Service;
import utilities.AuthUtility;
import utilities.JwtUtility;
import utilities.models.TokenResponse;

/**
 * Created by jiaweizhang on 10/29/2016.
 */

@org.springframework.stereotype.Service
public class AuthService extends Service {

    public String authorize(String code, String error, String state) {
        // validate hardcoded state variable
        if (!state.equals("spr")) {
            throw new AuthException("state does not equal spr");
        }

        if (error != null && !error.equals("")) {
            // error exists
            throw new AuthException("error within Spotify");
        }

        // get access_token and refresh_token
        TokenResponse tokenResponse = AuthUtility.tokenCode(code);
        System.out.println(tokenResponse.toString());

        // retrieve user information and store into database
        Api api = Api.builder()
                .accessToken(tokenResponse.access_token)
                .refreshToken(tokenResponse.refresh_token)
                .build();

        final CurrentUserRequest request = api.getMe().build();
        try {
            final User user = request.get();
            String userId = user.getId();
            String email = user.getEmail();

            // TODO

            // if user exists, update

            // else, add

            // Store userId, email, accessToken, refreshToken into database

            // generate jwt token
            return JwtUtility.generateToken(userId);
        } catch (Exception e) {
            return null;
        }
    }
}
