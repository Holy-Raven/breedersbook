package ru.codesquad.userinfo.dto;

import lombok.experimental.UtilityClass;
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
                .build();
        return userInfoDto;
    }

    public static UserInfo returnUserInfo(UserInfoNewDto userInfoNewDto) {
        UserInfo userInfo = UserInfo.builder()
                .description(userInfoNewDto.getDescription())
                .address(userInfoNewDto.getAddress())
                .phone(userInfoNewDto.getPhone())
                .photo(userInfoNewDto.getPhoto())
                .birthDate(userInfoNewDto.getBirthDate())
                .created(LocalDateTime.now())
                .build();
        return userInfo;
    }
}