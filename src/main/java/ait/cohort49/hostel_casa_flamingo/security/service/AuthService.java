package ait.cohort49.hostel_casa_flamingo.security.service;


import ait.cohort49.hostel_casa_flamingo.model.dto.UserDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Role;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.RoleRepository;
import ait.cohort49.hostel_casa_flamingo.repository.UserRepository;
import ait.cohort49.hostel_casa_flamingo.security.dto.LoginRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.RegisterRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.TokenResponseDTO;
import ait.cohort49.hostel_casa_flamingo.service.mapping.UserMappingService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.UserMappingServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    public static final String DEFAULD_USER_ROLE_NAME = "ROLE_USER";
    private final TokenService tokenService;
    private final UserDetailsService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Map<String, String> refreshStorage;
    private final UserRepository userRepository;
    private final UserMappingService userMappingService;
    private final RoleRepository roleRepository;


    public AuthService(TokenService tokenService,
                       UserDetailsService userService,
                       BCryptPasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       UserMappingService userMappingService, RoleRepository roleRepository) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMappingService = userMappingService;
        this.refreshStorage = new HashMap<>();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) throws AuthenticationException {
        String username = loginRequestDTO.userEmail();
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

    public UserDto register(RegisterRequestDTO loginRequestDTO) {
        String hashPassword = passwordEncoder.encode(loginRequestDTO.password());
        String normalizedEmail = loginRequestDTO.userEmail().toLowerCase().trim();
        Optional<User> foundUser = userRepository.findUserByEmail(normalizedEmail);
        if (foundUser.isPresent()) {
            throw new IllegalArgumentException("User with email " + normalizedEmail + " already exist");
        }

        User user = new User(loginRequestDTO.firstName(),
                loginRequestDTO.lastName(),
                normalizedEmail,
                loginRequestDTO.phoneNumber(),
                hashPassword);
        roleRepository.findRoleByTitle(DEFAULD_USER_ROLE_NAME)
                .ifPresent(r -> user.getRoles().add(r));
        userRepository.save(user);
        return userMappingService.mapEntityToDto(user);
    }
}
