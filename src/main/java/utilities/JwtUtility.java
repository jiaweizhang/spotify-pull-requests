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
        Properties p = PropertiesLoader.loadPropertiesFromPackage("security.properties");
        String secretKey = p.getProperty("jwt_secret");

        return Jwts.builder().setSubject(spotifyId)
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public static Claims retrieveClaims(String jwt) {
        Properties p = PropertiesLoader.loadPropertiesFromPackage("security.properties");
        String secretKey = p.getProperty("secretKey");

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
    }
}