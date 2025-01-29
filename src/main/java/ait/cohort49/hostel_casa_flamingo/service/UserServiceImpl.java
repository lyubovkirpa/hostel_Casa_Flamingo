package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.entity.ConfirmationCode;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.ConfirmationCodeRepository;
import ait.cohort49.hostel_casa_flamingo.repository.UserRepository;
import ait.cohort49.hostel_casa_flamingo.security.dto.RegisterRequestDTO;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.EmailService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ConfirmationCodeRepository confirmationCodeRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService, ConfirmationCodeRepository confirmationCodeRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.confirmationCodeRepository = confirmationCodeRepository;
    }


    @Override
    public void register(RegisterRequestDTO registerRequestDTO) {

    }

    @Override
    public String confirmEmail(String code) {
        Optional<ConfirmationCode> optionalConfirmationCode =confirmationCodeRepository.findByCode(code);
        if (optionalConfirmationCode.isEmpty()){
            throw new RuntimeException("Confirmation code not found");
        }
        ConfirmationCode confirmationCode =optionalConfirmationCode.get();
         if(confirmationCode.getExpired().isAfter(LocalDateTime.now())){

             User user = confirmationCode.getUser();
             user.setIsConfirmed(true);
             userRepository.save(user);

             return user.getEmail() + "confirmed!";
         }
         throw new RuntimeException("Wrong Email address");


    }
}
