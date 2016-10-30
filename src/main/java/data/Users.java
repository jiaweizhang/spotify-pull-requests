package data;

import java.sql.Timestamp;

public class Users {
    public String spotifyId;
    public String email;
    public String authorizationCode;
    public String refreshToken;
    public String accessToken;
    public Timestamp expiration;

    public Users(String spotifyId, String email, String authorizationCode, String refreshToken, String accessToken, Timestamp expiration) {
        this.spotifyId = spotifyId;
        this.email = email;
        this.authorizationCode = authorizationCode;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.expiration = expiration;
    }
}
