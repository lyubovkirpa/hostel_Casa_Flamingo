package ait.cohort49.hostel_casa_flamingo.model.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class CreateOrUpdateRoomDto {

    private final String number;
    private final String type;
    private final BigDecimal price;

    public CreateOrUpdateRoomDto(String number, String type, BigDecimal price) {
        this.number = number;
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "CreateOrUpdateRoomDto{" +
                "number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateOrUpdateRoomDto that)) return false;
        return Objects.equals(number, that.number) && Objects.equals(type, that.type) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type, price);
    }
}
