package ait.cohort49.hostel_casa_flamingo.security.dto;

import ait.cohort49.hostel_casa_flamingo.constants.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record RegisterRequestDTO(@NotBlank(message = "{validation.email.not_blank}")
                                 @Pattern(
                                         regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                                         message = "{validation.email.invalid_format}"
                                 )
                                 String userEmail,

                                 @NotBlank(message = "{validation.password.not_blank}")
                                 @Pattern(
                                         regexp = ValidationConstants.PASSWORD_REGEX,
                                         message = "{validation.password.invalid}"
                                 )
                                 String password,

                                 @NotBlank(message = "{validation.phone_number.not_blank}")
                                 @Pattern(
                                         regexp = "^\\+?[0-9]{10,15}$",
                                         message = "{validation.phone_number.invalid}"
                                 )
                                 String phoneNumber,

                                 @NotBlank(message = "{validation.first_name.not_blank}")
                                 @Pattern(regexp = "^[A-Za-z]{2,50}$",
                                         message = "{validation.first_name.invalid}")
                                 String firstName,

                                 @NotBlank(message = "{validation.last_name.not_blank}")
                                         @Pattern(regexp = "^[A-Za-z]{2,50}$",
                                                 message = "{validation.last_name.invalid}")
                                 String lastName
) {
}
