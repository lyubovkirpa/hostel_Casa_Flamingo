package ait.cohort49.hostel_casa_flamingo.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;


@Schema(description = "Class that describes Bed")
public class BedDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String type;

    private BigDecimal price;


    @Override
    public String toString() {
        return String.format("Bed: id - %d, number - %s, type - %s, price - %s",
                id, number, type, price);
    }

    public Long getId() {
        return id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BedDto bedDto = (BedDto) o;
        return Objects.equals(id, bedDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
