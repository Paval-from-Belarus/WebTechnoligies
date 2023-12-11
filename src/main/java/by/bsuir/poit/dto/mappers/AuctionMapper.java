package by.bsuir.poit.dto.mappers;

import by.bsuir.poit.dto.AuctionDto;
import by.bsuir.poit.dto.AuctionTypeDto;
import by.bsuir.poit.model.Auction;
import by.bsuir.poit.model.AuctionType;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuctionMapper {
@Mapping(target = "startDate", source = "eventDate")
@Mapping(target = "lots", ignore = true)
@Mapping(target = "endDate", ignore = true)
@Mapping(target = "bets", ignore = true)
@Mapping(target = "auctionType", ignore = true)
@Mapping(target = "admin", ignore = true)
Auction toEntity(AuctionDto auctionDto);

@Mapping(target = "adminId", source = "admin.id")
@Mapping(target = "auctionTypeId", source = "auctionType.id")
@Mapping(target = "eventDate", source = "startDate")
AuctionDto toDto(Auction auction);

@BeanMapping(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
Auction partialUpdate(AuctionDto auctionDto, @MappingTarget Auction auction);

AuctionTypeDto toDto(AuctionType auctionType);
}