package ru.codesquad.userinfo.dto;

import lombok.experimental.UtilityClass;
import ru.codesquad.user.User;
import ru.codesquad.user.dto.MapperUser;
import ru.codesquad.userinfo.UserInfo;

import java.time.LocalDateTime;

@UtilityClass
public class MapperUserInfo {

    public UserInfoDto returnUserInfoDto(UserInfo userInfo) {
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .description(userInfo.getDescription())
                .address(userInfo.getAddress())
                .phone(userInfo.getPhone())
                .photo(userInfo.getPhoto())
                .birthDate(userInfo.getBirthDate())
                .owner(MapperUser.returnUserInfoShortDto(userInfo.getOwner()))
                .build();
        return userInfoDto;
    }




    public static UserInfo returnUserInfo(UserInfoNewDto userInfoNewDto, User user) {
        UserInfo userInfo = UserInfo.builder()
                .description(userInfoNewDto.getDescription())
                .address(userInfoNewDto.getAddress())
                .phone(userInfoNewDto.getPhone())
                .photo(userInfoNewDto.getPhoto())
                .birthDate(userInfoNewDto.getBirthDate())
                .created(LocalDateTime.now())
                .owner(user)
                .build();
        return userInfo;
    }
}