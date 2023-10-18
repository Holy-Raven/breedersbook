package ru.codesquad.userinfo;

import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.userinfo.dto.UserInfoNewDto;
import ru.codesquad.userinfo.dto.UserInfoUpdateDto;

public interface UserInfoService {

    UserInfoDto addUserInfo(UserInfoNewDto userInfoNewDto, Long userId);

    UserInfoDto updateUserInfo(UserInfoUpdateDto userInfoUpdateDto, Long userId);

    boolean deleteUserInfo(Long yourId);

    UserInfoDto getUserInfoById(Long yourId);
}
