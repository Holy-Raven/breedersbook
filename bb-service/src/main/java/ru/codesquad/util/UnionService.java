package ru.codesquad.util;

import ru.codesquad.kennel.location.Location;
import ru.codesquad.user.User;
import ru.codesquad.userinfo.UserInfo;

public interface UnionService {
    User getUserOrNotFound(Long userId);

    UserInfo getUserInfoOrNotFound(Long userInfoId);

    Location getLocationOrNotFound(Long locationId);

    String checkPhoneNumber(String number);
}
