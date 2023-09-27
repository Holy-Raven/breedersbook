package ru.codesquad.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.codesquad.kennel.dto.KennelMapper;
import ru.codesquad.kennel.location.LocationMapper;
import ru.codesquad.user.User;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.userinfo.dto.UserInfoMapper;
import ru.codesquad.util.enums.Gender;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(uses = {UserInfoMapper.class, KennelMapper.class, LocationMapper.class},
        componentModel = "spring",
        imports = {LocalDateTime.class, UUID.class, Gender.class, UserInfoDto.class},
        disableSubMappingMethodsGeneration = true)
public interface UserMapper {

    UserDto returnUserDto(User user);

    @Mapping(target = "gender", expression = "java(Gender.getGenderValue(userNewDto.getGender()))")
    @Mapping(target = "created", expression = "java(LocalDateTime.now())")
    User returnUser(UserNewDto userNewDto);

    UserShortDto returnUserShortDto(User user);
}
