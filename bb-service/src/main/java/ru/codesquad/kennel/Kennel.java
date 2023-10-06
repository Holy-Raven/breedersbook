package ru.codesquad.kennel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.kennel.location.Location;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import static ru.codesquad.util.Constant.DATE_TIME_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kennels", schema = "public")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Kennel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Long id;

    @Column(name = "name", nullable = false, unique = true)
    @Size(max = 250)
    String name;

    @Column(name = "description")
    @Size(max = 5000)
    String descriptions;

    @Column(name = "phone_number", unique = true)
    @Size(max = 20)
    String phone;

    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    LocalDateTime created;

    @Column(name = "photo_url")
    @Size(max = 2048)
    String photo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    Location location;
}
