package ru.codesquad.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }

    @Override
    public Role getAdminRole() {
        return roleRepository.findByName("ROLE_ADMIN").get();
    }

    @Override
    public Role getBreederRole() {
        return roleRepository.findByName("ROLE_BREEDER").get();
    }

    @Override
    public Role getClubAdminRole() {
        return roleRepository.findByName("ROLE_CLUB_ADMIN").get();
    }

    @Override
    public Role getClubModeratorRole() {
        return roleRepository.findByName("ROLE_CLUB_MODERATOR").get();
    }
}
