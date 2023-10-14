package ru.codesquad.user;

import ru.codesquad.user.dto.UserDto;
import ru.codesquad.user.dto.UserNewDto;
import ru.codesquad.user.dto.UserShortDto;
import ru.codesquad.user.dto.UserUpdateDto;
import java.util.List;

public interface UserService {

    UserDto addUser(UserNewDto userNewDto);

    UserDto getPrivateUserById(Long yourId);

    UserShortDto getPublicUserById(Long userId);

    UserDto updateUser(Long yourId, UserUpdateDto userUpdateDto);

    boolean deleteUser(Long userId);

    boolean deleteUserByAdmin(Long userId, String typeDelete);

    List<UserDto> getAllUsers(Integer from, Integer size);
}
