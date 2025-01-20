package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.RoomDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface RoomMappingService {

    Room mapDtoToEntity(RoomDto roomDto);

    RoomDto mapEntityToDto(Room entity);
}
