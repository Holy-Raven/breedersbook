package ru.codesquad.userinfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository <UserInfo, Long> {
}
