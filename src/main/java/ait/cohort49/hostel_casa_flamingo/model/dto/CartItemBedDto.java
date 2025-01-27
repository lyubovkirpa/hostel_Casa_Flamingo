package ait.cohort49.hostel_casa_flamingo.model.dto;

import java.time.ZonedDateTime;
import java.util.Objects;


public class CartItemBedDto {

    private Long id;
    private ZonedDateTime entryDate;
    private ZonedDateTime departureDate;
    private BedDto bed;
    private Long cartId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public ZonedDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(ZonedDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public BedDto getBed() {
        return bed;
    }

    public void setBed(BedDto bed) {
        this.bed = bed;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemBedDto that = (CartItemBedDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("CartItemBed: id - %d, entryDate - %s, departureDate - %s, bed - %s, cartId - %s",
                id, entryDate, departureDate, bed, cartId);
    }
}
