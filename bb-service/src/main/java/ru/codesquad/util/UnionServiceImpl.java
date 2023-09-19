package ru.codesquad.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.userinfo.UserInfoRepository;
import ru.codesquad.userinfo.UserInfoService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnionServiceImpl implements  UnionService {


    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;

    @Override
    public User getUserOrNotFound(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new NotFoundException(User.class, "User id " + userId + " not found.");
        } else {
            return user.get();
        }
    }

    @Override
    public UserInfo getUserInfoOrNotFound(Long userInfoId) {

        Optional<UserInfo> userInfo = userInfoRepository.findById(userInfoId);

        if (userInfo.isEmpty()) {
            throw new NotFoundException(User.class, "UserInfo id " + userInfoId + " not found.");
        } else {
            return userInfo.get();
        }
    }
}
