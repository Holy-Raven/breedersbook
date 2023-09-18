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

    public static UserInfo returnUserInfo(UserInfoDto userInfoDto) {
        UserInfo userInfo = UserInfo.builder()
                .id(userInfoDto.getId())
                .description(userInfoDto.getDescription())
                .address(userInfoDto.getAddress())
                .phone(userInfoDto.getPhone())
                .photo(userInfoDto.getPhoto())
                .birthDate(userInfoDto.getBirthDate())
                .created(LocalDateTime.now())
                .build();
        return userInfo;
    }
}
