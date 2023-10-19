package ru.codesquad.show.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

import static ru.codesquad.util.Constant.DATE_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "awards", schema = "public")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Long id;

    @Column(name = "show_rank", nullable = false)
    String showRank;

    @Column(name = "club", nullable = false)
    String club;

    @Column(name = "age_class", nullable = false)
    String ageClass;

    @Column(name = "mark", nullable = false)
    String mark;

    @Column(name = "title")
    String title;

    @Column(name = "date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    LocalDate date;

    @Column(name = "pet_id", nullable = false)
    long petId;

    @Column(name = "photo_url")
    String photoUrl;
}