package ru.codesquad.vac.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.vac.enums.VacType;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vacs", schema = "public")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description", nullable = false)
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    VacType type;
}
