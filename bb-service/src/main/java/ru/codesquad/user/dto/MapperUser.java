package ru.codesquad.user.dto;

import lombok.experimental.UtilityClass;
import ru.codesquad.kennel.dto.MapperKennel;
import ru.codesquad.user.User;
import ru.codesquad.userinfo.dto.MapperUserInfo;
import ru.codesquad.util.enums.Gender;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MapperUser {

    public UserDto returnUserDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .gender(user.getGender())
                .userInfo(MapperUserInfo.returnUserInfoDto(user.getUserInfo()))
                .kennel(MapperKennel.returnKennelDto(user.getKennel()))
                .build();
        return userDto;
    }

    public UserShortDto returnUserShortDto(User user) {
        UserShortDto userShortDto = UserShortDto.builder()
                .name(user.getName())
                .gender(user.getGender())
                .userInfo(MapperUserInfo.returnUserInfoDto(user.getUserInfo()))
                .build();
        return userShortDto;
    }

    public static User returnUser(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .login(userDto.getLogin())
                .gender(userDto.getGender())
                .userInfo(MapperUserInfo.returnUserInfo(userDto.getUserInfo()))
                .kennel(MapperKennel.returnKennel(userDto.getKennel()))
                .build();
        return user;
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