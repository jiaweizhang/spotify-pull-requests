package data;

import java.sql.Timestamp;

public class Users {
    public String mySpotifyUserID;
    public String myEmail;
    public String myAuthToken;
    public String myRefreshToken;
    public String myAccessToken;
    public Timestamp myExpiration;

    public Users(String spotifyUserID, String email, String authToken, String refreshToken, String accessToken, Timestamp expiration) {
        mySpotifyUserID = spotifyUserID;
        myEmail = email;
        myAuthToken = authToken;
        myRefreshToken = refreshToken;
        myAccessToken = accessToken;
        myExpiration = expiration;
    }
}
