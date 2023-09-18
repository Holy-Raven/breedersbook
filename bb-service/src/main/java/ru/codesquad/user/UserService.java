package ru.codesquad.user;

import ru.codesquad.user.dto.UserDto;
import ru.codesquad.user.dto.UserNewDto;

public interface UserService {

    UserDto addUser(UserNewDto userNewDto);

}
