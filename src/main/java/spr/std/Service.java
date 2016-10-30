package spr.std;

import com.wrapper.spotify.Api;
import data.Users;
import data.data_accessors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import spr.exceptions.AuthException;
import spr.std.models.StdRequest;
import utilities.AuthUtility;
import utilities.models.TokenResponse;

import java.sql.Timestamp;

/**
 * Created by jiaweizhang on 10/29/2016.
 */

@org.springframework.stereotype.Service
public class Service {

    public final AuthAccessor authAccessor = new AuthAccessor();
    public final PlaylistAccessor playlistAccessor = new PlaylistAccessor();
    public final PlaylistPrAccessor playlistPrAccessor = new PlaylistPrAccessor();
    public final RequestAccessor requestAccessor = new RequestAccessor();
    public final ContributorAccessor contributorAccessor = new ContributorAccessor();
    public final VotesAccessor votesAccessor = new VotesAccessor();

    @Autowired
    public JdbcTemplate jt;

    public void updateRequest(StdRequest stdRequest) {
        if (authAccessor.isExist(stdRequest.spotifyId)) {
            // if exists, check that access_token is not expired
            Users user = authAccessor.getUser(stdRequest.spotifyId);
            System.out.println("Updating request");
            System.out.println(user.accessToken);
            System.out.println(user.expiration);
            if (user.expiration.before(new Timestamp(System.currentTimeMillis()))) {
                // expired
                TokenResponse tokenResponse = AuthUtility.tokenRefresh(user.refreshToken);
                if (tokenResponse.access_token == null) {
                    throw new AuthException("LOLOLOLOL");
                }
                user.accessToken = tokenResponse.access_token;
                user.expiration = new Timestamp(System.currentTimeMillis() + 3500 * 1000);

                // update access token and expiration
                authAccessor.updateUser(user);
            }
            stdRequest.accessToken = "Bearer " + user.accessToken;
            System.out.println(stdRequest.accessToken);
            stdRequest.api = Api.builder().accessToken(user.accessToken).build();
        } else {
            // user does not exist
            throw new AuthException("User does not exist");
        }
    }

    public Api getApi(String spotifyId) {
        if (authAccessor.isExist(spotifyId)) {
            // if exists, check that access_token is not expired
            Users user = authAccessor.getUser(spotifyId);
            if (user.expiration.before(new Timestamp(System.currentTimeMillis()))) {
                // expired
                TokenResponse tokenResponse = AuthUtility.tokenRefresh(user.refreshToken);
                if (tokenResponse.access_token == null) {
                    throw new AuthException("LOLOLOLOL2");
                }
                user.accessToken = tokenResponse.access_token;
                user.expiration = new Timestamp(System.currentTimeMillis() + 3500 * 1000);

                // update access token and expiration
                authAccessor.updateUser(user);
            }
            return Api.builder().accessToken(user.accessToken).build();
        } else {
            // user does not exist
            throw new AuthException("User does not exist");
        }
    }
}
