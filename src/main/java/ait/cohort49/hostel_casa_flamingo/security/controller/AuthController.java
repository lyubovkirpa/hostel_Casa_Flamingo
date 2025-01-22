package ait.cohort49.hostel_casa_flamingo.security.controller;


import ait.cohort49.hostel_casa_flamingo.security.dto.LoginRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.RefreshRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.TokenResponseDTO;
import ait.cohort49.hostel_casa_flamingo.security.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) throws AuthenticationException {
        return authService.login(loginRequestDTO);
    }

    @PostMapping("/refresh")
    public TokenResponseDTO refreshToken (@RequestBody RefreshRequestDTO refreshRequestDTO){

        try {
            return authService.refreshAccessToken(refreshRequestDTO.refreshToken());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

    }
}
