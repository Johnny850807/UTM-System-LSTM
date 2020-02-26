package app.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */
public class JwtUtil {

    static final long expirationTime = 432_000_000;
    public static String createJWS(String pilotId){
        ResourceBundle resource = ResourceBundle.getBundle("secret");
        String secret = resource.getString("jwt.secret");
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("pilotId",pilotId)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))// 過期時間
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean authenticateJws(){
        return true;
    }
}
