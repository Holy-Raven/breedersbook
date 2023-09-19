package ru.codesquad.userinfo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.exception.ConflictException;
import ru.codesquad.user.User;
import ru.codesquad.userinfo.dto.MapperUserInfo;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.userinfo.dto.UserInfoNewDto;
import ru.codesquad.userinfo.dto.UserInfoUpdateDto;
import ru.codesquad.util.UnionService;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UnionService unionService;

    @Override
    public UserInfoDto addUserInfo(UserInfoNewDto userInfoNewDto, Long userId) {

        User user = unionService.getUserOrNotFound(userId);

        UserInfo userInfo = MapperUserInfo.returnUserInfo(userInfoNewDto, user);

        return MapperUserInfo.returnUserInfoDto(userInfo);
    }

    @Override
    public UserInfoDto updateUserInfo(UserInfoUpdateDto userInfoUpdateDto, Long userId, Long userInfoId) {

        User user = unionService.getUserOrNotFound(userId);
        UserInfo userInfo = unionService.getUserInfoOrNotFound(userInfoId);

        if (!userInfo.getOwner().equals(user)) {
            throw new ConflictException(String.format("User %s can only update his personal information",userId));
        }

        if (userInfoUpdateDto.getDescription() != null && !userInfoUpdateDto.getDescription().isBlank()) {
            userInfo.setDescription(userInfoUpdateDto.getDescription());
        }
        if (userInfoUpdateDto.getAddress() != null && !userInfoUpdateDto.getAddress().isBlank()) {
            userInfo.setAddress(userInfoUpdateDto.getAddress());
        }
        if (userInfoUpdateDto.getPhone() != null && !userInfoUpdateDto.getPhone().isBlank()) {
            userInfo.setPhone(userInfoUpdateDto.getPhone());
        }
        if (userInfoUpdateDto.getPhoto() != null && !userInfoUpdateDto.getPhoto().isBlank()) {
            userInfo.setPhoto(userInfoUpdateDto.getPhoto());
        }
        if (userInfoUpdateDto.getBirthDate() != null && !userInfoUpdateDto.getBirthDate().isBefore(LocalDateTime.now())) {
            userInfo.setBirthDate(userInfoUpdateDto.getBirthDate());
        }

        userInfo.setOwner(user);
        userInfo = userInfoRepository.save(userInfo);

        return MapperUserInfo.returnUserInfoDto(userInfo);
    }
}
