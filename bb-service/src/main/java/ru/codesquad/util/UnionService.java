package ru.codesquad.util;

import ru.codesquad.user.User;
import ru.codesquad.userinfo.UserInfo;

public interface UnionService {
    User getUserOrNotFound(Long userId);

    UserInfo getUserInfoOrNotFound(Long userInfoId);

    String checkPhoneNumber(String number);
}
