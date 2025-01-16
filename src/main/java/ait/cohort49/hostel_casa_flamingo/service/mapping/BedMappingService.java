package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.service.BedGetDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BedMappingService {

    Bed mapDtoToEntity(BedGetDto bedGetDto);

    BedDto mapEntityToDto(Bed entity);
}
