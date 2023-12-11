package by.bsuir.poit.dto.mappers;

import by.bsuir.poit.dto.ClientDto;
import by.bsuir.poit.model.Client;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {
@Mapping(target = "user", ignore = true)
Client toEntity(ClientDto clientDto);

ClientDto toDto(Client client);

@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
Client partialUpdate(ClientDto clientDto, @MappingTarget Client client);
}