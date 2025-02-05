package ait.cohort49.hostel_casa_flamingo.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class CreateOrUpdateRoomDto {

    @NotBlank(message = "Room number must not be empty or blank")
    @Pattern(regexp = "^\\S+$", message = "Bed number must not contain spaces")
    private final String number;

    @NotBlank(message = "Room type must not be empty or blank")
    @Pattern(regexp = "^\\S+$", message = "Bed type must not contain spaces")
    private final String type;


    public CreateOrUpdateRoomDto(String number, String type) {
        this.number = number;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "CreateOrUpdateRoomDto{" +
                "number='" + number + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateOrUpdateRoomDto that)) return false;
        return Objects.equals(number, that.number) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type);
    }
}
