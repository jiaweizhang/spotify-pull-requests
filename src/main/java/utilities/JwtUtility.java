package utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Properties;

/**
 * Created by jiaweizhang on 10/29/2016.
 */
public class JwtUtility {
    public static String generateToken(String spotifyId) {
        String secretKey = getSecretKey();

        return Jwts.builder().setSubject(spotifyId)
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public static Claims retrieveClaims(String jwt) {
        String secretKey = getSecretKey();

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
    }

    public static String retrieveSpotifyId(String jwt) {
        return retrieveClaims(jwt).getSubject();
    }

    private static String getSecretKey() {
        Properties p = PropertiesLoader.loadPropertiesFromPackage("security.properties");
        return p.getProperty("jwt_secret");
    }
}