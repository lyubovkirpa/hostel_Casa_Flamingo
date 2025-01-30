package ait.cohort49.hostel_casa_flamingo.model.dto;

import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;
import java.util.Objects;


public class CartDatesDto {

    @FutureOrPresent
    private LocalDate entryDate;

    private LocalDate departureDate;

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDatesDto that = (CartDatesDto) o;
        return Objects.equals(entryDate, that.entryDate) && Objects.equals(departureDate, that.departureDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryDate, departureDate);
    }

    @Override
    public String toString() {
        return "CartDatesDto{" +
                "entryDate=" + entryDate +
                ", departureDate=" + departureDate +
                '}';
    }
}
