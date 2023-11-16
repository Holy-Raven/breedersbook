package ru.codesquad.role;

public interface RoleService {

    Role getUserRole();

    Role getAdminRole();

    Role getBreederRole();

    Role getClubUserRole();

    Role getClubAdminRole();

    Role getClubModeratorRole();

}
