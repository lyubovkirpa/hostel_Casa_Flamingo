package ait.cohort49.hostel_casa_flamingo.exception;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Andrej Reutow
 * created on 07.12.2023
 */

@Schema(description = "DTO for representing exception responses")
public record ExceptionResponseDto(String message, Integer statusCode) {
}
