package by.bsuir.poit.dto.mappers;

import by.bsuir.poit.dto.UserDto;
import by.bsuir.poit.model.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

@Mapping(target = "userStatus", ignore = true)
@Mapping(target = "salt", ignore = true)
@Mapping(target = "hash", ignore = true)
User toEntity(UserDto userDto);

UserDto toDto(User user);

@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
User partialUpdate(UserDto userDto, @MappingTarget User user);
}