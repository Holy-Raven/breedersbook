package ru.codesquad.userinfo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.exception.ConflictException;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.userinfo.dto.*;
import ru.codesquad.util.UnionService;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
    private final UnionService unionService;

    private final UserInfoMapper userInfoMapper = Mappers.getMapper(UserInfoMapper.class);


    @Override
    @Transactional
    public UserInfoDto addUserInfo(UserInfoNewDto userInfoNewDto, Long userId) {

        User user = unionService.getUserOrNotFound(userId);
        UserInfo userInfo = userInfoMapper.returnUserInfo(userInfoNewDto);

        userInfo = userInfoRepository.save(userInfo);

        user.setUserInfo(userInfo);
        userRepository.save(user);

        System.out.println(user);

        return userInfoMapper.returnUserInfoDto(userInfo);
    }

    @Override
    @Transactional
    public UserInfoDto updateUserInfo(UserInfoUpdateDto userInfoUpdateDto, Long userId, Long userInfoId) {

        User user = unionService.getUserOrNotFound(userId);
        UserInfo userInfo = unionService.getUserInfoOrNotFound(userInfoId);

        if (!user.getUserInfo().getId().equals(userInfoId)) {
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

        userInfo = userInfoRepository.save(userInfo);

        return userInfoMapper.returnUserInfoDto(userInfo);
    }
}
