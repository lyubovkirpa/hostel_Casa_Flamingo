package ait.cohort49.hostel_casa_flamingo.security.dto;

import ait.cohort49.hostel_casa_flamingo.constants.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequestDTO(@NotBlank
                                 @Email
                                 String userEmail,
                                 @NotBlank
                                 @Pattern(regexp = ValidationConstants.PASSWORD_REGEX)
                                 String password,
                                 String phoneNumber,
                                 String firstName,
                                 String lastName) {

}
