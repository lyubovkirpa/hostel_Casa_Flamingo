package ait.cohort49.hostel_casa_flamingo.model.dto;

import java.util.Objects;

public class CreateOrUpdateRoomDto {

    private final String number;
    private final String type;


    public CreateOrUpdateRoomDto(String number, String type) {
        this.number = number;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "CreateOrUpdateRoomDto{" +
                "number='" + number + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateOrUpdateRoomDto that)) return false;
        return Objects.equals(number, that.number) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type);
    }
}
