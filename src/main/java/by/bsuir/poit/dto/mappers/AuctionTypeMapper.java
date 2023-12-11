package by.bsuir.poit.dto.mappers;

import by.bsuir.poit.dto.AuctionTypeDto;
import by.bsuir.poit.model.AuctionType;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuctionTypeMapper {
@Mapping(target = "schemaName", ignore = true)
AuctionType toEntity(AuctionTypeDto auctionTypeDto);

AuctionTypeDto toDto(AuctionType auctionType);

@BeanMapping(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
AuctionType partialUpdate(AuctionTypeDto auctionTypeDto, @MappingTarget AuctionType auctionType);
}