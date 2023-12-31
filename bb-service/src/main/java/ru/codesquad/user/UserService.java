package ru.codesquad.user;

import ru.codesquad.user.dto.*;

import java.util.List;

public interface UserService {

    UserDto addUser(UserNewDto userNewDto);

    UserDto getPrivateUserById(Long yourId);

    UserShortDto getPublicUserById(Long userId);

    UserDto updateUser(Long yourId, UserUpdateDto userUpdateDto);

    boolean updateUserPassword(Long yourId, UserDtoUpdatePass userDtoUpdatePass);

    boolean deleteUser(Long userId);

    boolean deleteUserByAdmin(Long userId, String typeDelete);

    List<UserDto> getAllUsers(Integer from, Integer size);
}
