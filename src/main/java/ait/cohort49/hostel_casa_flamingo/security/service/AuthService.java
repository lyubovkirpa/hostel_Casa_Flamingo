package ait.cohort49.hostel_casa_flamingo.security.service;


import ait.cohort49.hostel_casa_flamingo.security.dto.LoginRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.TokenResponseDTO;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final TokenService tokenService;
    private final UserDetailsService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Map<String, String> refreshStorage;


    public AuthService(TokenService tokenService, UserDetailsService userService, BCryptPasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.refreshStorage = new HashMap<>();
    }

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) throws AuthenticationException {
        String username = loginRequestDTO.username();
        UserDetails foundUser = userService.loadUserByUsername(username);

        if (passwordEncoder.matches(loginRequestDTO.password(), foundUser.getPassword())) {
            String accessToken = tokenService.generateAccessToken(foundUser);
            String refreshToken = tokenService.generateRefreshToken(foundUser);
            refreshStorage.put(username, refreshToken);

            return new TokenResponseDTO(accessToken, refreshToken);
        }

        throw new AuthenticationException("Incorrect login and/ or password ");
    }

    public TokenResponseDTO refreshAccessToken(String refreshToken) throws AuthenticationException {
        boolean isValid = tokenService.validateRefreshToken(refreshToken);
        if (!isValid) {
            throw new AuthenticationException("Incorrect refresh token. Re login please");
        }
        Claims refreshClaims = tokenService.getRefreshClaimsFromToken(refreshToken);
        String username = refreshClaims.getSubject();

        String savedToken = refreshStorage.getOrDefault(username, null);
        boolean isSaved = savedToken != null && savedToken.equals(refreshToken);

        if (isSaved) {
            UserDetails foundUser = userService.loadUserByUsername(username);

            String accessToken = tokenService.generateAccessToken(foundUser);

            return new TokenResponseDTO(accessToken, refreshToken);
        }
        throw new AuthenticationException("Incorrect refresh token. Re login please");

    }
}
