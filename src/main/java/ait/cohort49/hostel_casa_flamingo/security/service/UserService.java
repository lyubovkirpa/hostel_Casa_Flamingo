package ait.cohort49.hostel_casa_flamingo.security.service;

import ait.cohort49.hostel_casa_flamingo.model.dto.UserDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.UserRepository;
import ait.cohort49.hostel_casa_flamingo.service.mapping.UserMappingService;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMappingService userMappingService;

    public UserService(UserRepository userRepository, UserMappingService userMappingService) {
        this.userRepository = userRepository;
        this.userMappingService = userMappingService;
    }

    public UserDto findUserByEmailOrThrow(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User with email: " + userEmail + " not found"));
        return userMappingService.mapEntityToDto(user);
    }

    public User getUserByEmailOrThrow(String userEmail) {
        return userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User with email: " + userEmail + " not found"));
    }
}
