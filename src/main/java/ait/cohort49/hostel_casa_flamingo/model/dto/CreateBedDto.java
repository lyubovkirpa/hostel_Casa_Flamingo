package ait.cohort49.hostel_casa_flamingo.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;


@Schema(description = "Class that describes Bed")
public class CreateBedDto {

    @NotBlank(message = "Bed number must not be empty or blank")
    @Pattern(regexp = "^[^\\s]+$", message = "Bed number must not contain spaces")
    private String number;

    @NotBlank(message = "Bed type must not be empty or blank")
    @Pattern(regexp = "^[^\\s]+$", message = "Bed type must not contain spaces")
    private String type;

    @PositiveOrZero(message = "Price must be non-negative")
    @Digits(integer = 10, fraction = 1, message = "Price must be a valid number with at most one digit after the decimal")
    @NotBlank(message = "Bed number must not be empty or blank")
    private BigDecimal price;

    private Long roomId;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return String.format("Bed: number - %s, type - %s, price - %s, roomId - %s",
                number, type, price, roomId);
    }
}
