package utilities.models;

/**
 * Created by jiaweizhang on 10/29/2016.
 */
public class TokenResponse {
    public String access_token;
    public String token_type;
    public String scope;
    public int expires_in;
    public String refresh_token;

    @Override
    public String toString() {
        return "access_token: " + access_token
                + "\ntoken_type: " + token_type
                + "\nscope: " + scope
                + "\nexpires_in: " + expires_in
                + "\nrefresh_token: " + refresh_token;
    }
}
