package com.epam.esm.controller;

import com.epam.esm.security.configSecury.AuthService;
import com.epam.esm.security.model.JwtRequest;
import com.epam.esm.security.model.JwtResponse;
import com.epam.esm.security.model.RefreshJwtRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping("/api/v1.1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * @param authRequest JwtRequest (nickName, email and password)
     * @return refresh and access tokens
     * @throws AuthException
     */
    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        JwtResponse token = authService.userName(authRequest);
        return ResponseEntity.ok(token);
    }

    /**
     * @param request refresh jwt token
     * @return new access token
     * @throws AuthException
     */
    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    /**
     * @param request refresh jwt token
     * @return new refresh and access tokens
     * @throws AuthException
     */
    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
