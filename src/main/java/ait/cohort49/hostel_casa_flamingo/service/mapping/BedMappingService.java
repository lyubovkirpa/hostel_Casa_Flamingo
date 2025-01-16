package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface BedMappingService {

    @Mapping(target = "id", ignore = true)
    Bed mapDtoToEntity(BedDto dto);

    BedDto mapEntityToDto(Bed entity);
}
