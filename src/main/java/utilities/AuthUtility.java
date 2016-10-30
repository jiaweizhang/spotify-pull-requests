package utilities;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import spr.exceptions.AuthException;
import utilities.models.TokenResponse;

import java.util.Properties;

/**
 * Created by jiaweizhang on 10/29/2016.
 */
public class AuthUtility {
    private final static String TOKEN_ROOT = "https://accounts.spotify.com/api/token";
    private final static Gson gson = new Gson();

    public static TokenResponse tokenCode(String code) {
        HttpRequestWithBody http = Unirest.post(TOKEN_ROOT);

        String authorizationHeader = getAuthorizationHeader();
        String redirectUrl = getRedirectUrl();

        try {
            HttpResponse<String> httpResponse = http
                    .queryString("code", code)
                    .queryString("grant_type", "authorization_code")
                    .queryString("redirect_uri", redirectUrl)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
                    .header("Authorization", authorizationHeader)
                    .asString();
            return gson.fromJson(httpResponse.getBody(), TokenResponse.class);
        } catch (UnirestException e) {
            e.printStackTrace();
            throw new AuthException("Unirest error");
        }
    }

    public static TokenResponse tokenRefresh(String refreshToken) {
        HttpRequestWithBody http = Unirest.post(TOKEN_ROOT);

        String authorizationHeader = getAuthorizationHeader();

        try {
            HttpResponse<String> httpResponse = http
                    .queryString("refresh_token", refreshToken)
                    .queryString("grant_type", "authorization_code")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
                    .header("Authorization", authorizationHeader)
                    .asString();
            return gson.fromJson(httpResponse.getBody(), TokenResponse.class);
        } catch (UnirestException e) {
            e.printStackTrace();
            throw new AuthException("Unirest error");
        }
    }

    private static String getAuthorizationHeader() {
        //load authorization header
        Properties securityProperties = PropertiesLoader.loadPropertiesFromPackage("security.properties");
        return securityProperties.getProperty("token_auth_header");
    }

    private static String getRedirectUrl() {
        Properties securityProperties = PropertiesLoader.loadPropertiesFromPackage("security.properties");
        return securityProperties.getProperty("redirect");
    }
}
