package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.BookingDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BedMappingService.class, UserMappingService.class})
public interface BookingMappingService {


    BookingDto mapEntityToDto(Booking entity);
}
