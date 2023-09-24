package ru.codesquad.user.dto;

import lombok.experimental.UtilityClass;
import org.mapstruct.factory.Mappers;
import ru.codesquad.kennel.dto.KennelMapper;
import ru.codesquad.user.User;
import ru.codesquad.userinfo.dto.UserInfoMapper;
import ru.codesquad.util.enums.Gender;

import java.time.LocalDateTime;

@UtilityClass
public class MapperUser {
    private final KennelMapper kennelMapper = Mappers.getMapper(KennelMapper.class);
    private final UserInfoMapper userInfoMapper = Mappers.getMapper(UserInfoMapper.class);

    public UserDto returnUserDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .login(user.getLogin())
                .gender(user.getGender())
                .created(user.getCreated())
                .build();

        if (user.getKennel() != null) {
            userDto.setKennelDto(kennelMapper.returnKennelDto(user.getKennel()));
        }
        if (user.getUserInfo() != null) {
            userDto.setUserInfoDto(userInfoMapper.returnUserInfoDto(user.getUserInfo()));
        }

        return userDto;
    }
    public UserShortDto returnUserShortDto(User user) {
        UserShortDto userShortDto = UserShortDto.builder()
                .name(user.getName())
                .gender(user.getGender())
                .build();

        if (user.getUserInfo() != null) {
            userShortDto.setUserInfoDto(userInfoMapper.returnUserInfoDto(user.getUserInfo()));
        }

        return userShortDto;
    }
    public static User returnUser(UserNewDto UserNewDto) {
        User user = User.builder()
                .email(UserNewDto.getEmail())
                .name(UserNewDto.getName())
                .login(UserNewDto.getLogin())
                .gender(Gender.getGenderValue(UserNewDto.getGender()))
                .created(LocalDateTime.now())
                .build();
        return user;
    }
}