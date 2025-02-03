package ait.cohort49.hostel_casa_flamingo.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


public class CartDto {

    private Long id;
    private List<CartItemBedDto> beds;
    private Long countBeds;
    private BigDecimal totalPriceBeds;

    @JsonBackReference
    private UserDto userDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItemBedDto> getBeds() {
        return beds;
    }

    public void setBeds(List<CartItemBedDto> beds) {
        this.beds = beds;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public Long getCountBeds() {
        return countBeds;
    }

    public void setCountBeds(Long countBeds) {
        this.countBeds = countBeds;
    }

    public BigDecimal getTotalPriceBeds() {
        return totalPriceBeds;
    }

    public void setTotalPriceBeds(BigDecimal totalPriceBeds) {
        this.totalPriceBeds = totalPriceBeds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDto cartDto = (CartDto) o;
        return Objects.equals(id, cartDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("CartDto: id - %d, cartItemBeds - %s, countBeds- %s, totalPriceBeds - %s",
                this.id, beds, countBeds, totalPriceBeds);
    }
}
