package com.epam.esm.security.configSecury;

import com.epam.esm.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret.access}")
    private String secretKeyAccess;
    @Value("${jwt.secret.refresh}")
    private String secretKeyRefresh;
    @Value("${jwt.expiration.access}")
    private long accessTime;
    @Value("${jwt.expiration.refresh}")
    private long refreshTime;

    public String generateAccessToken(@NonNull User user) {
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusMinutes(accessTime).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getNickName())
                .setExpiration(accessExpiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyAccess)))
                .claim("roles", user.getRole())
                .claim("username", user.getNickName())
                .compact();
    }

    public String generateRefreshToken(@NonNull User user) {
        LocalDateTime now = LocalDateTime.now();
        Instant refreshExpirationInstant = now.plusDays(refreshTime).atZone(ZoneId.systemDefault()).toInstant();
        Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getNickName())
                .setExpiration(refreshExpiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyRefresh)))
                .compact();
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyAccess)));
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyRefresh)));
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
