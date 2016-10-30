package spr.std;

import com.wrapper.spotify.Api;
import data.Users;
import data.data_accessors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import spr.exceptions.AuthException;
import utilities.AuthUtility;
import utilities.models.TokenResponse;

import java.sql.Timestamp;

/**
 * Created by jiaweizhang on 10/29/2016.
 */

@org.springframework.stereotype.Service
public class Service {

    public final AuthAccessor authAccessor = new AuthAccessor();
    public final MasterPlaylistAccessor masterPlaylistAccessor = new MasterPlaylistAccessor();
    public final IndividualPlaylistAccessor individualPlaylistAccessor = new IndividualPlaylistAccessor();
    public final MasterSongAccessor masterSongAccessor = new MasterSongAccessor();
    public final VotesAccessor votesAccessor = new VotesAccessor();

    @Autowired
    public JdbcTemplate jt;

    public Api getApi(String spotifyId) {
        if (authAccessor.isExist(spotifyId)) {
            // if exists, check that access_token is not expired
            Users user = authAccessor.getUser(spotifyId);
            if (user.expiration.after(new Timestamp(System.currentTimeMillis()))) {
                // expired
                TokenResponse tokenResponse = AuthUtility.tokenRefresh(user.refreshToken);
                user.accessToken = tokenResponse.access_token;
                user.expiration = new Timestamp(System.currentTimeMillis() + 3500 * 1000);

                // update access token and expiration
                authAccessor.updateAccessTokenAndExpirationToken(user);
            }

            return Api.builder().accessToken(user.accessToken).build();
        } else {
            // user does not exist
            throw new AuthException("User does not exist");
        }
    }
}
