package by.bsuir.poit.dto.mappers;

import by.bsuir.poit.dto.ClientFeedbackDto;
import by.bsuir.poit.model.ClientFeedback;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientFeedbackMapper {
@Mapping(target = "targetClient", ignore = true)
@Mapping(target = "lot", ignore = true)
@Mapping(target = "authorClient", ignore = true)
ClientFeedback toEntity(ClientFeedbackDto clientFeedbackDto);
@Mapping(target = "targetId", source = "targetClient.id")
@Mapping(target = "authorId", source = "authorClient.id")
@Mapping(target = "lotId", source = "lot.id")
ClientFeedbackDto toDto(ClientFeedback clientFeedback);

@BeanMapping(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
ClientFeedback partialUpdate(ClientFeedbackDto clientFeedbackDto, @MappingTarget ClientFeedback clientFeedback);
}