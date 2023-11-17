package ru.codesquad.club.clubsusers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.club.Club;
import ru.codesquad.role.Role;
import ru.codesquad.user.User;
import ru.codesquad.util.enums.ClubRole;
import ru.codesquad.util.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static ru.codesquad.util.Constant.DATE_TIME_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clubs_users", schema = "public")
@IdClass(ClubsUsersId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClubsUsers {

    @Id
    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    Club club;

    @Id
    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

//    @EmbeddedId
//    @AttributeOverrides(value = {
//            @AttributeOverride(name = "club", column = @Column(name = "club_id")),
//            @AttributeOverride(name = "user", column = @Column(name = "user_id"))
//    })
//    ClubsUsersId clubsUsersId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    ClubRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status;

    @Column(name = "update")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    LocalDateTime created;
}


