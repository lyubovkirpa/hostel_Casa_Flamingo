package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.UserDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleMappingService.class})
public interface UserMappingService {

    @Mapping(target = "id", ignore = true)
    User mapDtoToEntity (UserDto userDto);

    UserDto mapEntityToDto(User entity);

}
