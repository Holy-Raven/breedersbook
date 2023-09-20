package ru.codesquad.user.dto;

import lombok.experimental.UtilityClass;
import ru.codesquad.kennel.dto.MapperKennel;
import ru.codesquad.user.User;
import ru.codesquad.userinfo.dto.MapperUserInfo;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.util.enums.Gender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class MapperUser {

    public UserDto returnUserDto(User user) {


        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .login(user.getLogin())
                .gender(user.getGender())
                .kennel(MapperKennel.returnKennelDto(user.getKennel()))
                .created(LocalDateTime.now())
                .build();

        if (user.getUserInfo() != null) {
            userDto.setUserInfo(MapperUserInfo.returnUserInfoDto(user.getUserInfo()));
        } else {
            userDto.setUserInfo(new UserInfoDto());
        }
        return userDto;
    }

    public UserShortDto returnUserShortDto(User user) {
        UserShortDto userShortDto = UserShortDto.builder()
                .name(user.getName())
                .gender(user.getGender())
                .build();

        if (user.getUserInfo() != null) {
            userShortDto.setUserInfo(MapperUserInfo.returnUserInfoDto(user.getUserInfo()));
        } else {
            userShortDto.setUserInfo(new UserInfoDto());
        }
        return userShortDto;
    }

    public static User returnUser(UserNewDto UserNewDto) {
        User user = User.builder()
                .email(UserNewDto.getEmail())
                .name(UserNewDto.getName())
                .login(UserNewDto.getLogin())
                .gender(Gender.getGenderValue(UserNewDto.getGender()))
                .build();
        return user;
    }

    public static List<UserDto> returnUserDtoList(Iterable<User> users) {
        List<UserDto> result = new ArrayList<>();

        for (User user : users) {
            result.add(returnUserDto(user));
        }
        return result;
    }
}