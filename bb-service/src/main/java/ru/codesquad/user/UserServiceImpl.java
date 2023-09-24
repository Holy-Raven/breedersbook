package ru.codesquad.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.exception.ConflictException;
import ru.codesquad.user.dto.*;
import ru.codesquad.userinfo.UserInfoRepository;
import ru.codesquad.util.UnionService;
import ru.codesquad.util.enums.Gender;

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
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Override
    @Transactional
    public UserDto addUser(UserNewDto userNewDto) {

        User user = userMapper.returnUser(userNewDto);
        user = userRepository.save(user);

        return MapperUser.returnUserDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getPrivateUserById(Long userId, Long yourId) {

        User user = unionService.getUserOrNotFound(userId);

        if (!user.getId().equals(yourId)) {
            throw new ConflictException(String.format("User %s can only update his account",userId));
        }

        return MapperUser.returnUserDto(userRepository.findById(userId).get());
    }

    @Override
    @Transactional(readOnly = true)
    public UserShortDto getPublicUserById(Long userId) {

        User user = unionService.getUserOrNotFound(userId);

        return MapperUser.returnUserShortDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long userId, Long yourId, UserUpdateDto userUpdateDto) {

        User user = unionService.getUserOrNotFound(userId);

        if (!user.getId().equals(yourId)) {
            throw new ConflictException(String.format("User %s can only update his account",userId));
        }

        if (userUpdateDto.getName() != null && !userUpdateDto.getName().isBlank()) {
            user.setName(userUpdateDto.getName());
        }

        if (userUpdateDto.getEmail() != null && !userUpdateDto.getEmail().isBlank()) {
            user.setEmail(userUpdateDto.getEmail());
        }

        if (userUpdateDto.getLogin() != null && !userUpdateDto.getLogin().isBlank()) {
            user.setLogin(userUpdateDto.getLogin());
        }

        if (userUpdateDto.getGender() != null && !userUpdateDto.getGender().isBlank()) {
            user.setGender(Gender.getGenderValue(userUpdateDto.getGender()));
        }

        user = userRepository.save(user);

        return MapperUser.returnUserDto(user);
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

        List<UserDto> result = new ArrayList<>();
        for (User user : userRepository.findAll(pageRequest)) {
            result.add(MapperUser.returnUserDto(user));
        }

        return result;
    }
}