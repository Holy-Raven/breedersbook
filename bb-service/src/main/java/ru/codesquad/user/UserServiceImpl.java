package ru.codesquad.user;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.exception.ValidationException;
import ru.codesquad.role.Role;
import ru.codesquad.role.RoleService;
import ru.codesquad.user.dto.*;
import ru.codesquad.userinfo.UserInfoRepository;
import ru.codesquad.util.UnionService;
import ru.codesquad.util.enums.Status;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final RoleService roleService;
    private final UnionService unionService;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto addUser(UserNewDto userNewDto) {

        User user = UserMapper.returnUser(userNewDto);

        user.setCreated(LocalDateTime.now());
        user.setStatus(Status.ACTIVE);

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getUserRole());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(userNewDto.getPassword()));

        user = userRepository.save(user);

        return UserMapper.returnUserDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getPrivateUserById(Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);

        return UserMapper.returnUserDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserShortDto getPublicUserById(Long userId) {

        User user = unionService.getUserOrNotFound(userId);

        return UserMapper.returnUserShortDto(user);
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

        return UserMapper.returnUserDto(user);
    }

    @Override
    @Transactional
    public boolean updateUserPassword(Long yourId, UserDtoUpdatePass userDtoUpdatePass) {

        User user = unionService.getUserOrNotFound(yourId);

        if (user.getPassword().equals(userDtoUpdatePass.getNewPassword())) {
            throw new ValidationException("Придуймате новый пароль");
        }

        if (user.getUsername().equals(userDtoUpdatePass.getUsername()) && user.getPassword().equals(userDtoUpdatePass.getPassword())) {
            user.setPassword(userDtoUpdatePass.getNewPassword());
            userRepository.save(user);
        } else {
            throw new ValidationException("Введенный вами логин или пароль не верны");
        }

        return true;
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {

        User user = unionService.getUserOrNotFound(userId);

        if (user.getUserInfo() != null) {
            userInfoRepository.deleteById(user.getUserInfo().getId());
        }

        user.setStatus(Status.DELETE);
        userRepository.save(user);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteUserByAdmin(Long userId, String typeDelete) {

        User user = unionService.getUserOrNotFoundByAdmin(userId);

        if (user.getUserInfo() != null) {
            userInfoRepository.deleteById(user.getUserInfo().getId());
        }

        if (typeDelete.equals("none")) {
            user.setStatus(Status.DELETE);
            userRepository.save(user);
        } else if (typeDelete.equals("full")) {
            userRepository.delete(user);
        } else {
            throw new ValidationException("Проверьте ваш запрос на удаление");
        }

        return true;
    }


    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers(Integer from, Integer size) {

        PageRequest pageRequest = PageRequest.of(from / size, size);

        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findAll(pageRequest).forEach(user -> userDtoList.add(UserMapper.returnUserDto(user)));

        return userDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
    }

    //берем USER из базы данных (ищем по username) и преобразуем в USERDETAILS
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByUsername(username);

        if (username.equals("admin")) {
            Set<Role> roles = user.getRoles();
            roles.add(roleService.getAdminRole());
            user.setRoles(roles);
            userRepository.save(user);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }
}