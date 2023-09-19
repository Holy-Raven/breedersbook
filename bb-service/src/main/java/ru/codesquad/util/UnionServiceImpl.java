package ru.codesquad.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnionServiceImpl implements  UnionService {


    private final UserRepository userRepository;

    @Override
    public User getUserOrNotFound(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new NotFoundException(User.class, "User id " + userId + " not found.");
        } else {
            return user.get();
        }
    }
}
