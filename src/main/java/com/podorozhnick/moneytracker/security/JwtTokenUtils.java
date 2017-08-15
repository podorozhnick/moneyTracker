package com.podorozhnick.moneytracker.security;

import com.podorozhnick.moneytracker.server.StartSettings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenUtils {

    private static final String CLAIM_NAME_KEY = "name";
    private static final String CLAIM_ROLE_KEY = "role";

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private StartSettings settings;

    public String generateToken(JwtUser jwtUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_NAME_KEY, jwtUser.getUsername());
        claims.put(CLAIM_ROLE_KEY, jwtUser.getRole());
        return generateToken(claims);
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + settings.getTokenExpiration() * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.get("name").toString();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
        );
    }


}
