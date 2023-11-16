package ru.codesquad.club.clubsusers;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.club.Club;
import ru.codesquad.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClubsUsersId implements Serializable {

    Club club;

    User user;
}

//@Embeddable
//@Setter
//@Getter
//public class ClubsUsersId implements Serializable {
//
//    @NotNull
////    @ManyToMany(fetch = FetchType.LAZY)
////    @JoinColumn(name = "club_id")
//    Club club;
//
//    @NotNull
////    @ManyToMany(fetch = FetchType.LAZY)
////    @JoinColumn(name = "user_id")
//    User user;
//
//    public ClubsUsersId(Club club, User user) {
//        this.club = club;
//        this.user = user;
//    }
//    public ClubsUsersId() {
//    }
//}