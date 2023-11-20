package ru.codesquad.club.clubsusers;

import java.io.Serializable;
import java.util.Objects;

public class ClubsUsersId implements Serializable {

    Long clubId;

    Long userId;


    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClubsUsersId)) return false;
        ClubsUsersId that = (ClubsUsersId) o;
        return Objects.equals(getClubId(), that.getClubId()) && Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClubId(), getUserId());
    }
}