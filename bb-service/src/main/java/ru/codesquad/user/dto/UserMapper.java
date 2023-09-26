package ru.codesquad.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.codesquad.kennel.dto.KennelMapper;
import ru.codesquad.user.User;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.userinfo.dto.UserInfoMapper;
import ru.codesquad.util.enums.Gender;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring",
        imports = {LocalDateTime.class, UUID.class, Gender.class, UserInfoDto.class},
        disableSubMappingMethodsGeneration = true)
public abstract class UserMapper {

    @Autowired
    protected UserInfoMapper userInfoMapper;
    @Autowired
    protected KennelMapper kennelMapper;

    @Mapping(target = "kennel", expression = "java(kennelMapper.returnKennelDto(user.getKennel()))")
    @Mapping(target = "userInfo", expression = "java(userInfoMapper.returnUserInfoDto(user.getUserInfo()))")
    public abstract UserDto returnUserDto(User user);

    @Mapping(target = "gender", expression = "java(Gender.getGenderValue(userNewDto.getGender()))")
    @Mapping(target = "created", expression = "java(LocalDateTime.now())")
    public abstract User returnUser(UserNewDto userNewDto);

    public abstract UserShortDto returnUserShortDto(User user);
}
