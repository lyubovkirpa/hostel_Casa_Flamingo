package ait.cohort49.hostel_casa_flamingo.security.dto;

import ait.cohort49.hostel_casa_flamingo.constants.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record RegisterRequestDTO(@NotBlank(message = "{validation.email.not_blank}")
                                 @Email(message = "{validation.email.invalid}")
                                 String userEmail,

                                 @NotBlank(message = "{validation.password.not_blank}")
                                 @Pattern(
                                         regexp = ValidationConstants.PASSWORD_REGEX,
                                         message = "{validation.password.invalid}"
                                 )
                                 String password,

                                 @NotBlank(message = "{validation.phone_number.not_blank}")
                                 String phoneNumber,

                                 @NotBlank(message = "{validation.first_name.not_blank}")
                                 String firstName,

                                 @NotBlank(message = "{validation.last_name.not_blank}")
                                 String lastName
) {
}
