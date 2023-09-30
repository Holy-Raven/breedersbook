package ru.codesquad.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.exception.ValidationException;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.kennel.KennelRepository;
import ru.codesquad.kennel.location.Location;
import ru.codesquad.kennel.location.LocationRepository;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.userinfo.UserInfoRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnionServiceImpl implements  UnionService {


    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final LocationRepository locationRepository;
    private final KennelRepository kennelRepository;

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
            throw new NotFoundException(UserInfo.class, "UserInfo id " + userInfoId + " not found.");
        } else {
            return userInfo.get();
        }
    }

    @Override
    public Location getLocationOrNotFound(Long locationId) {

        Optional<Location> location = locationRepository.findById(locationId);

        if (location.isEmpty()) {
            throw new NotFoundException(Location.class, "Location id " + locationId + " not found.");
        } else {
            return location.get();
        }
    }

    @Override
    public Kennel getKennelOrNotFound(Long kennelId) {
        Optional<Kennel> kennel = kennelRepository.findById(kennelId);

        if (kennel.isEmpty()) {
            throw new NotFoundException(Kennel.class, "Kennel id " + kennelId + " not found.");
        } else {
            return kennel.get();
        }
    }

    @Override
    public String checkPhoneNumber(String number) {

        number = number.replaceAll("[^0-9]", "");

        if (number.length() == 10) {
            return "+7" + number;
        }
        if (number.length() == 11) {
            if (number.charAt(0) == '8') {
                return "+7" + number.substring(number.indexOf('8') + 1);
            } else if (number.charAt(0) == '7') {
                return "+" + number;
            } else if (number.charAt(0) != '7' || number.charAt(0) != '8') {
                throw new ValidationException("Invalid number format");
            }
        }

        throw new ValidationException("Invalid number format");
    }
}
