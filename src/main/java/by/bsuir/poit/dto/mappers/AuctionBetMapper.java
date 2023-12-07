package by.bsuir.poit.dto.mappers;

import by.bsuir.poit.dto.AuctionBetDto;
import by.bsuir.poit.model.AuctionBet;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuctionBetMapper {

@Mapping(target = "lot", ignore = true)
@Mapping(target = "client", ignore = true)
@Mapping(target = "auction", ignore = true)
AuctionBet toEntity(AuctionBetDto auctionBetDto);

@Mapping(target = "lotId", source = "lot.id")
@Mapping(target = "auctionId", source = "auction.id")
AuctionBetDto toDto(AuctionBet auctionBet);

@BeanMapping(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
AuctionBet partialUpdate(AuctionBetDto auctionBetDto, @MappingTarget AuctionBet auctionBet);
}