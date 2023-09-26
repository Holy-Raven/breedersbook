package ru.codesquad.breed;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.pet.enums.PetType;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "breeds", schema = "public")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "pet_type", nullable = false)
    private PetType petType;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "fur_type", nullable = false)
    private FurType furType;

    @Column(name = "photo_url")
    String photoUrl;
}