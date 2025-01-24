package ait.cohort49.hostel_casa_flamingo.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;
import java.util.Objects;


public class CartDto {

    private Long id;
    private List<BedDto> beds;

    private List<RoomDto> rooms;

    @JsonBackReference
    private UserDto userDto;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
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
        return String.format("CartDto: id - %d, beds - %s, rooms - %s",
                this.id, beds == null ? 0 : beds.size(), rooms == null ? 0 : rooms.size());
    }
}
