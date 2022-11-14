package com.epam.esm.security.filter;

import com.epam.esm.exception.NoSuchEntityException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class TokenValidator {

    @Value("${jwt.secret.access}")
    private String secretKeyAccess;
    @Value("${jwt.secret.refresh}")
    private String secretKeyRefresh;

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;

        } catch (Exception e) {
            throw  new NoSuchEntityException("Tokin is not valid");// TODO
        }
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyAccess)));
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyRefresh)));
    }

}
