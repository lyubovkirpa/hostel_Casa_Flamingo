package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.security.dto.RegisterRequestDTO;

public interface UserService {
   void register (RegisterRequestDTO registerRequestDTO);
   String confirmEmail(String code);
}
