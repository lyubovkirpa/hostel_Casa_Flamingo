package ait.cohort49.hostel_casa_flamingo.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(@NotBlank(message = "{validation.email.not_blank}")
                              @Email(message = "{validation.email.invalid}")
                              String userEmail,

                              @NotBlank(message = "{validation.password.not_blank}")
                              String password
) {
}
