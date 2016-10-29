package data;

public class Users {
    public String mySpotifyUserID;
    public String myEmail;
    public String myAuthToken;
    public String myRefreshToken;

    public Users(String spotifyUserID, String email, String authToken, String refreshToken) {
        mySpotifyUserID = spotifyUserID;
        myEmail = email;
        myAuthToken = authToken;
        myRefreshToken = refreshToken;
    }
}
