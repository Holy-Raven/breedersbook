package ru.codesquad.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.codesquad.kennel.dto.KennelMapper;
import ru.codesquad.user.User;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.userinfo.dto.UserInfoMapper;
import ru.codesquad.util.enums.Gender;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(uses = {UserInfoMapper.class, KennelMapper.class}, componentModel = "spring", imports = {LocalDateTime.class, UUID.class, Gender.class, UserInfoDto.class})
public interface UserMapper {
//
//    @Mapping(source = "user.userInfo", target = "userInfoDto", defaultExpression = "java(new UserInfoDto())")
//    @Mapping(source = "user.kennel", target ="kennelDto", defaultExpression = "java(null)")
//    UserDto returnUserDto(User user);
//
//    @Mapping(target = "gender", expression = "java(Gender.getGenderValue(userNewDto.getGender()))")
//    @Mapping (target = "created", expression = "java(LocalDateTime.now())")
//    User returnUser(UserNewDto userNewDto);
//
//    @Mapping(source = "userInfo", target = "userInfoDto", ignore = true)
//    UserShortDto returnUserShortDto(User user);


    @Mapping(target = "userInfoDto", source = "user.userInfo", defaultExpression = "java(null)")
    @Mapping(target ="kennelDto", source = "user.kennel", defaultExpression = "java(null)")
    UserDto returnUserDto(User user);

    @Mapping(target = "gender", expression = "java(Gender.getGenderValue(userNewDto.getGender()))")
    @Mapping (target = "created", expression = "java(LocalDateTime.now())")
    User returnUser(UserNewDto userNewDto);

    @Mapping(target = "userInfoDto", source = "user.userInfo", defaultExpression = "java(null)")
    UserShortDto returnUserShortDto(User user);
}
