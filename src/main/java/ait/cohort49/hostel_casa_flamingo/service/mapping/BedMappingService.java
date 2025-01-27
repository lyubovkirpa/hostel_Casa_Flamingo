package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.CreateBedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BedMappingService {

    Bed mapDtoToEntity(CreateBedDto createBedDto);

    BedDto mapEntityToDto(Bed entity);
}
