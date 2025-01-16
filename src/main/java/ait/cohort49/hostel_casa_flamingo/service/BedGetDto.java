package ait.cohort49.hostel_casa_flamingo.service;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;


@Schema(description = "Class that describes Bed")
public class BedGetDto {

    private String number;
    private String type;
    private BigDecimal price;


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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BedGetDto bedGetDto = (BedGetDto) o;
        return Objects.equals(number, bedGetDto.number) && Objects.equals(type, bedGetDto.type) && Objects.equals(price, bedGetDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type, price);
    }

    @Override
    public String toString() {
        return String.format("Bed: number - %s, type - %s, price - %s",
                number, type, price);
    }
}
