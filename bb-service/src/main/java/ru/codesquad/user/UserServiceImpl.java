package ru.codesquad.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.exception.ConflictException;
import ru.codesquad.user.dto.*;
import ru.codesquad.util.UnionService;
import ru.codesquad.util.enums.Gender;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UnionService unionService;

    @Override
    @Transactional
    public UserDto addUser(UserNewDto userNewDto) {

        User user = MapperUser.returnUser(userNewDto);
        userRepository.save(user);

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

        unionService.getUserOrNotFound(userId);

        return MapperUser.returnUserShortDto(userRepository.findById(userId).get());
    }

    @Override
    public UserDto updateUser(Long userId, UserUpdateDto userUpdateDto) {

        User user = unionService.getUserOrNotFound(userId);

        if (!user.getId().equals(userUpdateDto.getId())) {
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
    public void deletePrivateUser(Long userId, Long yourId) {

        User user = unionService.getUserOrNotFound(userId);

        if (!user.getId().equals(yourId)) {
            throw new ConflictException(String.format("User %s can only delete his account",userId));
        }

        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public void deleteAdminUser(Long userId) {

        unionService.getUserOrNotFound(userId);
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers(Integer from, Integer size) {

        PageRequest pageRequest = PageRequest.of(from / size, size);

        return MapperUser.returnUserDtoList(userRepository.findAll(pageRequest));
    }
}