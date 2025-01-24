package ait.cohort49.hostel_casa_flamingo.service;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Schema(description = "Class that describes Bed")
public class BedCreateDto {

    private String number;
    private String type;
    private BigDecimal price;
    private Long roomId;
    private List<Long> bookingIds = new ArrayList<>();

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

    public List<Long> getBookingIds() {
        return bookingIds;
    }

    public void setBookingIds(List<Long> bookingIds) {
        this.bookingIds = bookingIds;
    }

    @Override
    public String toString() {
        return String.format("Bed: number - %s, type - %s, price - %s, roomId - %s, bookingIds - %s",
                number, type, price, roomId, bookingIds);
    }
}
