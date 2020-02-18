package app.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class TokenCreator {

    public static void create(){
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")

                .setIssuedAt(new Date()) // 簽發時間
                .setExpiration(new Date(new Date().getTime() + 10000L))// 過期時間
                .setSubject("shawnwuID")
                .setIssuer("nutn")
                .claim("user_id", "20191016001")

                //簽證
                .signWith(SignatureAlgorithm.HS256, "DRONE".getBytes())
                .compact();

        System.out.println(new Date(new Date().getTime() + 10000L));
    }
}
