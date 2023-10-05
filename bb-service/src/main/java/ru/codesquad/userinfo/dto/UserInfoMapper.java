package ru.codesquad.userinfo.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.codesquad.userinfo.UserInfo;

@Mapper(componentModel = "spring",
        disableSubMappingMethodsGeneration = true)
public interface UserInfoMapper {

    @Mapping(target = "id", ignore = true)
    UserInfo returnUserInfo(UserInfoNewDto userInfonewDto);

    UserInfoDto returnUserInfoDto(UserInfo userInfo);
}
