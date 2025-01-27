package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMappingService.class, CartItemBedMappingService.class})
public interface CartMappingService {

    @Mapping(target = "userDto", source = "user")
    @Mapping(target = "beds", source = "cartItemBeds")
    CartDto mapEntityToDto(Cart entity);
}
