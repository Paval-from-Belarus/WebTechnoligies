package by.bsuir.poit.dto.mappers;

import by.bsuir.poit.dto.EnglishLotDto;
import by.bsuir.poit.dto.LotDto;
import by.bsuir.poit.model.EnglishLot;
import by.bsuir.poit.model.Lot;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface LotMapper {
@Mapping(target = "sellerClient", ignore = true)
@Mapping(target = "deliveryPoint", ignore = true)
@Mapping(target = "customerClient", ignore = true)
@Mapping(target = "auctionType", ignore = true)
@Mapping(target = "auction", ignore = true)
@Mapping(target = "auctionPrice", source = "actualPrice")
Lot toEntity(LotDto lotDto);

default LotDto toDto(Lot lot) {
      LotDto.LotDtoBuilder<?, ?> builder = LotDto.builder();
      mapDtoBuilder(builder, lot);
      return builder.build();
}

default LotDto toEnglishLotDto(EnglishLot englishLot) {
      EnglishLotDto.EnglishLotDtoBuilder<?, ?> builder = EnglishLotDto.builder();
      mapDtoBuilder(builder, englishLot.getLot());
      return builder.redemptionPrice(englishLot.getRedemptionPrice().doubleValue()).build();
}

@Mapping(target = "auctionId", source = "auction.id")
@Mapping(target = "deliveryPointId", source = "deliveryPoint.id")
@Mapping(target = "customerId", source = "customerClient.id")
@Mapping(target = "sellerId", source = "sellerClient.id")
@Mapping(target = "auctionTypeId", source = "auctionType.id")
@Mapping(target = "actualPrice", source = "auctionPrice")
void mapDtoBuilder(@MappingTarget LotDto.LotDtoBuilder<?, ?> builder, Lot lot);


@BeanMapping(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
Lot partialUpdate(LotDto lotDto, @MappingTarget Lot lot);
}