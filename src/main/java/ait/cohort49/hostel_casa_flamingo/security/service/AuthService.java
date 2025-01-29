package ait.cohort49.hostel_casa_flamingo.security.service;


import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.dto.UserDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Role;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.RoleRepository;
import ait.cohort49.hostel_casa_flamingo.repository.UserRepository;
import ait.cohort49.hostel_casa_flamingo.security.dto.LoginRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.RegisterRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.TokenResponseDTO;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.EmailService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoleService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.UserMappingService;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static ait.cohort49.hostel_casa_flamingo.service.mapping.UserEmailUtils.normalizeUserEmail;

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
    private final RoleService roleService;
    private final EmailService emailService;


    public AuthService(TokenService tokenService,
                       UserDetailsService userService,
                       BCryptPasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       UserMappingService userMappingService, RoleRepository roleRepository, RoleService roleService, EmailService emailService) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMappingService = userMappingService;
        this.emailService = emailService;
        this.refreshStorage = new HashMap<>();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;

    }

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {
        String username = normalizeUserEmail(loginRequestDTO.userEmail());
        UserDetails foundUser = userService.loadUserByUsername(username);

        if (passwordEncoder.matches(loginRequestDTO.password(), foundUser.getPassword())) {
            String accessToken = tokenService.generateAccessToken(foundUser);
            String refreshToken = tokenService.generateRefreshToken(foundUser);
            refreshStorage.put(username, refreshToken);

            return new TokenResponseDTO(accessToken, refreshToken);
        }

        throw new RestException(HttpStatus.UNAUTHORIZED, "Incorrect login and/ or password");

    }

    public TokenResponseDTO refreshAccessToken(String refreshToken) {
        boolean isValid = tokenService.validateRefreshToken(refreshToken);
        if (!isValid) {
            throw new RestException(HttpStatus.FORBIDDEN, "Incorrect refresh token. Re login please");
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
        throw new RestException(HttpStatus.FORBIDDEN, "Incorrect refresh token. Re login please");
    }
@Transactional
    public UserDto register(RegisterRequestDTO loginRequestDTO) {
        String hashPassword = passwordEncoder.encode(loginRequestDTO.password());
        String normalizedEmail = normalizeUserEmail(loginRequestDTO.userEmail());
        Optional<User> foundUser = userRepository.findUserByEmail(normalizedEmail);
        if (foundUser.isPresent()) {
            throw new RestException(HttpStatus.BAD_REQUEST, "User with email '", loginRequestDTO.userEmail(), "' already exist");
        }

        Role userRole = roleService.findRoleByTitleOrThrow(DEFAULD_USER_ROLE_NAME);
        User user = new User(loginRequestDTO.firstName(),
                loginRequestDTO.lastName(),
                normalizedEmail,
                loginRequestDTO.phoneNumber(),
                hashPassword,
                userRole);

        userRepository.save(user);
        emailService.sendConfirmationEmail(user);

        return userMappingService.mapEntityToDto(user);



    }

}
