package ru.codesquad.userinfo.dto;

import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.Gender;

public class UserInfoMapper {
    public static UserInfo returnUserInfo(UserInfoNewDto userInfonewDto) {
        return UserInfo.builder()
                .description(userInfonewDto.getDescription())
                .phone(userInfonewDto.getPhone())
                .photo(userInfonewDto.getPhoto())
                .birthDate(userInfonewDto.getBirthDate())
                .gender(EnumUtil.getValue(Gender.class, userInfonewDto.getGender()))
                .build();
    }

    public static UserInfoDto returnUserInfoDto(UserInfo userInfo) {
        return UserInfoDto.builder()
                .description(userInfo.getDescription())
                .phone(userInfo.getPhone())
                .photo(userInfo.getPhoto())
                .birthDate(userInfo.getBirthDate())
                .gender(userInfo.getGender())
                .build();
    }
}
