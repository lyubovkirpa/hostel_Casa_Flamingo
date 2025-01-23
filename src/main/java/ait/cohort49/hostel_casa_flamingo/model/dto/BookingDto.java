package ait.cohort49.hostel_casa_flamingo.model.dto;

import java.time.ZonedDateTime;
import java.util.List;

public class BookingDto {
    private Long id;
    private String entry;
    private String departure;
    private List<BedDto> beds;
    private List<RoomDto> rooms;
    private UserDto user;
    private String bookingStatus;
    private ZonedDateTime bookingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public List<BedDto> getBeds() {
        return beds;
    }

    public void setBeds(List<BedDto> beds) {
        this.beds = beds;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public ZonedDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(ZonedDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Override
    public String toString() {
        return String.format("Booking: id - %d, entry - %s, departure - %s, user - %s, beds - %s, rooms - %s, bookingStatus - %s, bookingDate - %s",
                id, entry, departure, user == null ? "null" : user.toString(), beds == null ? "null" : beds.size(),
                rooms == null ? "null" : rooms.size(), bookingStatus, bookingDate == null ? "null" : bookingDate.toString());
    }
}
