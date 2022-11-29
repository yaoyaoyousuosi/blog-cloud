package org.cloud.blog.common.utils;

/**
 * @author 王豪杰
 * @Version 1.0
 */

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
/**
 * @author
 */
public class JwtUtils {

    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    public static String createJwtToken(String id, String userName){

        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("blog-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET) // HS256 对称加密
                .compact();

        return JwtToken;
    }

    public static String getJwtToken(String id, String username, String type){
        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("guli-security")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("username", username)
                .claim("type", type)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET) // HS256 对称加密
                .compact();

        return JwtToken;
    }

    public static boolean checkTokenSecurity(String jwtToken) {
        if(null == jwtToken || "".equals(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getUserType(String jwtToken) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return ((String) claims.get("type"));
    }

}