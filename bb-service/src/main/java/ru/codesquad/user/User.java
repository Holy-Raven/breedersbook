package ru.codesquad.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.util.enums.Gender;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.codesquad.util.Constant.DATE_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "login", unique = true)
    String login;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    Gender gender;


//    @Builder.Default
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_info_id")
//    UserInfo userInfo = new UserInfo();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id")
    UserInfo userInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kennel_id")
    Kennel kennel;

    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    LocalDateTime created;
}
