package by.bsuir.poit.dto.mappers;

import by.bsuir.poit.dto.DeliveryPointDto;
import by.bsuir.poit.model.DeliveryPoint;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeliveryPointMapper {
@Mapping(target = "deliveryPointName", source = "name")
DeliveryPoint toEntity(DeliveryPointDto deliveryPointDto);

@Mapping(target = "name", source = "deliveryPointName")
DeliveryPointDto toDto(DeliveryPoint deliveryPoint);

@BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
DeliveryPoint partialUpdate(DeliveryPointDto deliveryPointDto, @MappingTarget DeliveryPoint deliveryPoint);
}