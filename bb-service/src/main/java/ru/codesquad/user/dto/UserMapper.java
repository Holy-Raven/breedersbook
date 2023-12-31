package ru.codesquad.user.dto;

import ru.codesquad.kennel.dto.KennelMapper;
import ru.codesquad.location.LocationMapper;
import ru.codesquad.user.User;
import ru.codesquad.userinfo.dto.UserInfoMapper;

public class UserMapper {

    public static UserDto returnUserDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .created(user.getCreated())
                .roles(user.getRoles())
                .status(user.getStatus())
                .build();

        if (user.getUserInfo() != null) {
            userDto.setUserInfo(UserInfoMapper.returnUserInfoDto(user.getUserInfo()));
        }

        if (user.getLocation() != null) {
            userDto.setLocation(LocationMapper.returnLocationDto(user.getLocation()));
        }

        if (user.getKennel() != null) {
            userDto.setKennel(KennelMapper.returnKennelDto(user.getKennel()));
        }

        return userDto;
    }

    public static UserShortDto returnUserShortDto(User user) {
        UserShortDto userShortDto = UserShortDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();

        if (user.getUserInfo() != null) {
            userShortDto.setUserInfo(UserInfoMapper.returnUserInfoDto(user.getUserInfo()));
        }

        return userShortDto;
    }

    public static User returnUser(UserNewDto userNewDto) {
        return User.builder()
                .firstName(userNewDto.getFirstName())
                .lastName(userNewDto.getLastName())
                .email(userNewDto.getEmail())
                .username(userNewDto.getUsername())
                .password(userNewDto.getPassword())
                .build();
    }
}
