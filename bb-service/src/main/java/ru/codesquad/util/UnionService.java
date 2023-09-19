package ru.codesquad.util;

import ru.codesquad.user.User;

public interface UnionService {
    User getUserOrNotFound(Long userId);
}
