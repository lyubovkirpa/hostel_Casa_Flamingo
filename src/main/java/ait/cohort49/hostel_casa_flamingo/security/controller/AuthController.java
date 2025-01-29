package ait.cohort49.hostel_casa_flamingo.security.controller;


import ait.cohort49.hostel_casa_flamingo.model.dto.UserDto;
import ait.cohort49.hostel_casa_flamingo.security.dto.LoginRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.RefreshRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.RegisterRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.TokenResponseDTO;
import ait.cohort49.hostel_casa_flamingo.security.service.AuthService;
import ait.cohort49.hostel_casa_flamingo.service.UserService;
import ait.cohort49.hostel_casa_flamingo.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;


    public AuthController(AuthService authService, UserService userService, UserServiceImpl userServiceImpl) {
        this.authService = authService;
        this.userService = userService;
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody @Valid RegisterRequestDTO loginRequestDTO) {
        return authService.register(loginRequestDTO);
    }

    @GetMapping("/confirm")
    public Response confirm(@RequestParam String code){
        return new Response(userService.confirmEmail(code));

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
