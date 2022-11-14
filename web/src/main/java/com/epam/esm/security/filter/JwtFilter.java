package com.epam.esm.security.filter;

import com.epam.esm.security.configSecury.JwtAuthentication;
import com.epam.esm.security.configSecury.JwtProvider;
import com.epam.esm.security.configSecury.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    @Value("${jwt.header}")
    private String authorizationHeader;

    private final JwtProvider jwtProvider;
    private final TokenValidator tokenValidator;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        String token = getTokenFromRequest((HttpServletRequest) request);
        if (token != null && tokenValidator.validateAccessToken(token)) {
            Claims claims = jwtProvider.getAccessClaims(token);
            JwtAuthentication jwtInfoToken = JwtUtils.generate(claims);
            jwtInfoToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
        }
        filterChain.doFilter(request, response);
    }
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(authorizationHeader);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer")) {
            return bearer.substring(7);
        }
        return null;
    }
}
