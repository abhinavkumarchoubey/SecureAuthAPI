package com.securauth.securauthapi.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private int jwtExpiration;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        List<String> auth = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+jwtExpiration);

        return Jwts.builder()
                .subject(Long.toString(userPrincipal.getId()))
                .claim("roles", auth)
                .claim("username", userPrincipal.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public Long getUserIdFromToken(String token){
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateToken(String authToken){
        try{
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        }
        catch (Exception e) {
//            throw  new RuntimeException("Invalid JWT signature");
        }
        return false;
    }

}
