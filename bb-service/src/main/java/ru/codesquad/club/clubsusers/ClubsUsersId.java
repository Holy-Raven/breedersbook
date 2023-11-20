package ru.codesquad.club.clubsusers;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClubsUsersId implements Serializable {

    Long clubId;

    Long userId;
}