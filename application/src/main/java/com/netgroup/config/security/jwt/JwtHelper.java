package com.netgroup.config.security.jwt;

import com.netgroup.config.security.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtHelper {
    private String jwtKey;

    public JwtHelper(String jwtKey) {
        this.jwtKey = jwtKey;
    }

    public String encode(TokenInfo tokenInfo, LocalDateTime expiration) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtKey.getBytes()), SignatureAlgorithm.HS512)
                .setSubject(tokenInfo.getUsername())
                .setExpiration(asDate(expiration))
                .claim("roles", tokenInfo.getRolesAsString())
                .compact();
    }

    private Date asDate(LocalDateTime expiration) {
        return Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant());
    }

    public TokenInfo decode(String token) {
        token = token.replace("Bearer ", "");

        Claims body = Jwts.parserBuilder()
                .setSigningKey(jwtKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return new TokenInfo(
                body.getSubject(),
                body.get("roles", String.class)
        );
    }
}
