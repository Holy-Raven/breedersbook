package ru.codesquad.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.user.dto.*;
import ru.codesquad.userinfo.UserInfoRepository;
import ru.codesquad.util.UnionService;
import ru.codesquad.util.enums.Status;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final UnionService unionService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto addUser(UserNewDto userNewDto) {

        User user = userMapper.returnUser(userNewDto);

        System.out.println();
        System.out.println(user);
        System.out.println();

        user.setStatus(Status.ACTIVE);
        user = userRepository.save(user);

        return userMapper.returnUserDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getPrivateUserById(Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);

        return userMapper.returnUserDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserShortDto getPublicUserById(Long userId) {

        User user = unionService.getUserOrNotFound(userId);

        return userMapper.returnUserShortDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long yourId, UserUpdateDto userUpdateDto) {

        User user = unionService.getUserOrNotFound(yourId);

        if (userUpdateDto.getFirstName() != null && !userUpdateDto.getFirstName().isBlank()) {
            user.setFirstName(userUpdateDto.getFirstName());
        }
        if (userUpdateDto.getLastName() != null && !userUpdateDto.getLastName().isBlank()) {
            user.setLastName(userUpdateDto.getLastName());
        }
        if (userUpdateDto.getEmail() != null && !userUpdateDto.getEmail().isBlank()) {
            user.setEmail(userUpdateDto.getEmail());
        }

        user = userRepository.save(user);

        return userMapper.returnUserDto(user);
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {

        User user = unionService.getUserOrNotFound(userId);

        if (user.getUserInfo() != null) {
            userInfoRepository.deleteById(user.getUserInfo().getId());
        }
        userRepository.deleteById(userId);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers(Integer from, Integer size) {

        PageRequest pageRequest = PageRequest.of(from / size, size);

        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findAll(pageRequest).forEach(user -> userDtoList.add(userMapper.returnUserDto(user)));

        return userDtoList;
    }
}