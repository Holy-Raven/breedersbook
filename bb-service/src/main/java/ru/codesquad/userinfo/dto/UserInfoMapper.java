package ru.codesquad.userinfo.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.EnumUtil;

import java.util.UUID;

@Mapper(componentModel = "spring",
        imports = {UUID.class, Gender.class, EnumUtil.class},
        disableSubMappingMethodsGeneration = true)
public interface UserInfoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gender", expression = "java(EnumUtil.getValue(Gender.class, userInfonewDto.getGender()))")
    UserInfo returnUserInfo(UserInfoNewDto userInfonewDto);

    UserInfoDto returnUserInfoDto(UserInfo userInfo);
}
