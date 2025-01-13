package ait.cohort49.hostel_casa_flamingo.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {

    private Long id;
    private int number;
    private String type;
    List<Bed> beds = new ArrayList<>();


    @Override
    public String toString() {
        return String.format("Room: id - %d, number - %s, type - %s, beds - %s",
                id, number, type, beds);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Bed> getBeds() {
        return beds;
    }

    public void setBeds(List<Bed> beds) {
        this.beds = beds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return number == room.number && Objects.equals(id, room.id) && Objects.equals(type, room.type) && Objects.equals(beds, room.beds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, type, beds);
    }
}
