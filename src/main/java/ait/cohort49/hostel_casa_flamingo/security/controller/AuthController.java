package ait.cohort49.hostel_casa_flamingo.security.controller;


import ait.cohort49.hostel_casa_flamingo.model.dto.UserDto;
import ait.cohort49.hostel_casa_flamingo.security.dto.LoginRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.RefreshRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.RegisterRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.TokenResponseDTO;
import ait.cohort49.hostel_casa_flamingo.security.service.AuthService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody @Valid RegisterRequestDTO loginRequestDTO) {
        return authService.register(loginRequestDTO);
    }

    @GetMapping("/confirm")
    public UserDto confirm(@RequestParam String code) {
        return userService.confirmUser(code);
    }

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }

    @PostMapping("/refresh")
    public TokenResponseDTO refreshToken(@RequestBody @Valid RefreshRequestDTO refreshRequestDTO) {
        return authService.refreshAccessToken(refreshRequestDTO.refreshToken());
    }
}
