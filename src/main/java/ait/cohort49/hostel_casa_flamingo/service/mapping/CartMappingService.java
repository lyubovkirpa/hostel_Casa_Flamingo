package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMappingService.class)
public interface CartMappingService {

    Cart mapDtoToEntity(CartDto cartDto);

    CartDto mapEntityToDto(Cart entity);
}
