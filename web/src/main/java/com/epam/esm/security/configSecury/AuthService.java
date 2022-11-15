package com.epam.esm.security.configSecury;

import com.epam.esm.entity.User;
import com.epam.esm.security.filter.TokenValidator;
import com.epam.esm.security.model.JwtRequest;
import com.epam.esm.security.model.JwtResponse;
import com.epam.esm.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.lettuce.core.support.caching.RedisCache;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceImpl userService;
    private final RedisCache<String, String> refreshStorage ;
    private final JwtProvider jwtProvider;
    private final TokenValidator validator;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse userName(@NonNull JwtRequest authRequest) throws AuthException {
        User user = userService.findByName(authRequest.getUsername());
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            String accessToken = jwtProvider.generateAccessToken(user);
            String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getNickName(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Is not valid password");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (validator.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String userName = claims.getSubject();
            String saveRefreshToken = refreshStorage.get(refreshToken);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                User user = userService.findByName(userName);
                String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
            return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (validator.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String userName = claims.getSubject();
            String saveRefreshToken = refreshStorage.get(refreshToken);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                User user = userService.findByName(userName);
                String accessToken = jwtProvider.generateAccessToken(user);
                String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getNickName(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("JWT token is not valid!"); //todo
    }

    @Bean
    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }


}
