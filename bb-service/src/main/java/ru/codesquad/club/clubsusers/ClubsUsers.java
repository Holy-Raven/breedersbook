package ru.codesquad.club.clubsusers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.util.enums.ClubRole;
import ru.codesquad.util.enums.Status;

import javax.persistence.*;
import java.time.LocalDate;

import static ru.codesquad.util.Constant.DATE_FORMAT;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clubs_users", schema = "public")
@IdClass(ClubsUsersId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClubsUsers {

    @Id
    @Column(name = "club_id", nullable = false)
    Long clubId;

    @Id
    @Column(name = "user_id", nullable = false)
    Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    ClubRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status;

    @Column(name = "update")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    LocalDate created;
}


