package ru.codesquad.userinfo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.userinfo.dto.UserInfoMapper;
import ru.codesquad.userinfo.dto.UserInfoNewDto;
import ru.codesquad.userinfo.dto.UserInfoUpdateDto;
import ru.codesquad.util.UnionService;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.Gender;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
    private final UnionService unionService;

    @Override
    @Transactional
    public UserInfoDto addUserInfo(UserInfoNewDto userInfoNewDto, Long userId) {

        User user = unionService.getUserOrNotFound(userId);
        UserInfo userInfo = UserInfoMapper.returnUserInfo(userInfoNewDto);

        userInfo.setPhone(unionService.checkPhoneNumber(userInfo.getPhone()));
        userInfo = userInfoRepository.save(userInfo);

        user.setUserInfo(userInfo);
        userRepository.save(user);

        return UserInfoMapper.returnUserInfoDto(userInfo);
    }

    @Override
    @Transactional
    public UserInfoDto updateUserInfo(UserInfoUpdateDto userInfoUpdateDto, Long userId) {

        User user = unionService.getUserOrNotFound(userId);
        UserInfo userInfo = unionService.getUserInfoOrNotFound(user.getUserInfo().getId());

        if (userInfoUpdateDto.getDescription() != null && !userInfoUpdateDto.getDescription().isBlank()) {
            userInfo.setDescription(userInfoUpdateDto.getDescription());
        }
        if (userInfoUpdateDto.getPhone() != null && !userInfoUpdateDto.getPhone().isBlank()) {
            userInfo.setPhone(unionService.checkPhoneNumber(userInfo.getPhone()));
        }
        if (userInfoUpdateDto.getPhoto() != null && !userInfoUpdateDto.getPhoto().isBlank()) {
            userInfo.setPhoto(userInfoUpdateDto.getPhoto());
        }
        if (userInfoUpdateDto.getBirthDate() != null && !userInfoUpdateDto.getBirthDate().isBefore(LocalDateTime.now())) {
            userInfo.setBirthDate(userInfoUpdateDto.getBirthDate());
        }
        if (userInfoUpdateDto.getGender() != null && !userInfoUpdateDto.getGender().isBlank()) {
            userInfo.setGender(EnumUtil.getValue(Gender.class, userInfoUpdateDto.getGender()));
        }

        userInfo = userInfoRepository.save(userInfo);

        return UserInfoMapper.returnUserInfoDto(userInfo);
    }

    @Override
    @Transactional
    public boolean deleteUserInfo(Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);
        UserInfo userInfo = unionService.getUserInfoOrNotFound(user.getUserInfo().getId());

        userInfoRepository.deleteById(userInfo.getId());

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoDto getUserInfoById(Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);
        UserInfo userInfo = unionService.getUserInfoOrNotFound(user.getUserInfo().getId());

        return UserInfoMapper.returnUserInfoDto(userInfo);
    }
}
